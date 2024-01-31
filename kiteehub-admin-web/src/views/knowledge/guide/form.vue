<template>
	<xn-form-container
		:title="formData.id ? '编辑业务指南' : '增加业务指南'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="分类：" name="categoryArr">
				<a-cascader
					v-model:value="formData.categoryArr"
					:options="categoryOptions"
					placeholder="请选择业务分类"
					change-on-select
					:fieldNames="{ children: 'children', label: 'name', value: 'id' }"
				/>
			</a-form-item>
			<a-form-item label="事项代码：" name="eventCode">
				<a-input v-model:value="formData.eventCode" placeholder="请输入事项代码" allow-clear />
			</a-form-item>
			<a-form-item label="办理对象：" name="handleObj">
				<a-input v-model:value="formData.handleObj" placeholder="请输入办理对象" allow-clear />
			</a-form-item>
			<a-form-item label="办理依据：" name="handleBasis">
				<a-input v-model:value="formData.handleBasis" placeholder="请输入办理依据" allow-clear />
			</a-form-item>
			<a-form-item label="办理部门：" name="handleDept">
				<a-input v-model:value="formData.handleDept" placeholder="请输入办理部门" allow-clear />
			</a-form-item>
			<a-form-item label="文本方式：" name="sendType">
				<a-radio-group v-model:value="sendType">
					<a-radio value="TXT">纯文本</a-radio>
					<a-radio value="HTML">HTML</a-radio>
				</a-radio-group>
			</a-form-item>
			<a-form-item label="正文" name="handleMaterial" v-if="sendType === 'TXT'">
				<a-textarea v-model:value="formData.handleMaterial" placeholder="请输入正文" :auto-size="{ minRows: 6, maxRows: 6 }" />
			</a-form-item>
			<a-form-item label="正文" name="handleMaterial" v-if="sendType === 'HTML'">
				<xn-editor v-model="formData.handleMaterial" placeholder="请输入正文" :height="200"></xn-editor>
			</a-form-item>
			<!-- <a-form-item label="办理材料：" name="handleMaterial">
				<a-textarea
					v-model:value="formData.handleMaterial"
					placeholder="请输入办理材料"
					:auto-size="{ minRows: 3, maxRows: 5 }"
				/>
			</a-form-item> -->
			<a-form-item label="办理时限：" name="handleTime">
				<a-input v-model:value="formData.handleTime" placeholder="请输入办理时限" allow-clear />
			</a-form-item>
			<a-form-item label="流程图：" name="flowChart">
				<a-upload
					v-model:file-list="fileList"
					name="file"
					:action="action"
					:headers="headers"
					list-type="picture-card"
					@change="handleChange"
					@preview="handlePreview"
				>
					<div>
						<plus-outlined />
						<div style="margin-top: 8px">上传</div>
					</div>
				</a-upload>
				<a-modal :open="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
					<img alt="example" style="width: 100%" :src="previewImage" />
				</a-modal>
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="kbWorkGuideForm">
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import kbWorkGuideApi from '@/api/knowledge/kbWorkGuideApi'
import sysConfig from '@/config/index'
import XnEditor from "@/components/Editor/index.vue";
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
const categoryOptions = ref([])

const action = ref(`${import.meta.env.VITE_API_BASEURL}/dev/file/uploadLocalReturnUrl`)
const headers = ref({})
const loading = ref(false)
const previewVisible = ref(false)
const previewImage = ref('')
const previewTitle = ref('')
const fileList = ref([])
// 文本方式
const sendType = ref('TXT')
const handleCancel = () => {
	previewVisible.value = false
	previewTitle.value = ''
}
const handlePreview = async (file) => {
	if (!file.url && !file.preview) {
		file.preview = await getBase64(file.originFileObj)
	}
	previewImage.value = file.url || file.preview
	previewVisible.value = true
	previewTitle.value = file.name || file.url.substring(file.url.lastIndexOf('/') + 1)
}
onMounted(() => {
	// getArea()
	getToken()
})
// 获取token
const getToken = () => {
	const token = tool.data.get('TOKEN')
	headers.value[sysConfig.TOKEN_NAME] = sysConfig.TOKEN_PREFIX + token
}
const handleChange = (info) => {
	if (info.file.status === 'uploading') {
		loading.value = true
		return
	}
	if (info.file.status === 'done') {
		console.log(info.file, 'info.file.originFileObj')

		formData.value.flowChart = info.file.response.data

		console.log(formData.flowChartCopy, 'formData.flowChartCopy')
		loading.value = false
	}
	if (info.file.status === 'error') {
		loading.value = false
		message.error('upload error')
	}
}

// 打开抽屉
const onOpen = (record) => {
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		console.log(recordData.flowChart, 'recordData.flowChart')
		fileList.value =
			[
				{
					uid: '-1',
					name: 'image.png',
					status: 'done',
					url: recordData.flowChart
				}
			] || []
		formData.value = Object.assign({}, recordData)
	}else{
		fileList.value = []
	}
	formData.value.categoryArr = formData.value.categoryArr && formData.value.categoryArr.split(',') // 转换为数组
	const DICT_TYPE_TREE_DATA = tool.data.get('DICT_TYPE_TREE_DATA')
	if (DICT_TYPE_TREE_DATA) {
		categoryOptions.value = [DICT_TYPE_TREE_DATA.find((item) => item.dictValue === 'OPERATION_FLOW')]
	}
}
// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	formData.value = {}
	visible.value = false
}
// 默认要校验的
const formRules = {
	categoryArr: [required('请选择分类')],
	eventCode: [required('请输入事项代码')],
	handleObj: [required('请输入办理对象')],
	handleBasis: [required('请输入办理依据')],
	handleDept: [required('请输入办理部门')],
	handleMaterial: [required('请输入办理材料')],
	handleTime: [required('请输入办理时限')]
}
// 验证并提交数据  
const onSubmit = () => {
	formRef.value.validate().then(() => {
		submitLoading.value = true
		let categoryArr = cloneDeep(formData.value.categoryArr)
		formData.value.category = categoryArr.pop()
		// formData.value.flowChart = formData.value.flowChart || formData.value.flowChartCopy[0].response.data
		console.log(formData.value ,'formData.value.flowChart formData.value.flowChart ')
		// return
		formData.value.categoryArr = cloneDeep(formData.value.categoryArr).join(',')
		const formDataParam = cloneDeep(formData.value)
		kbWorkGuideApi
			.kbWorkGuideSubmitForm(formDataParam, formDataParam.id)
			.then(() => {
				onClose()
				emit('successful')
			})
			.finally(() => {
				submitLoading.value = false
			})
	})
}
// 抛出函数
defineExpose({
	onOpen
})
</script>


<style scoped>
.ant-upload-select-picture-card i {
	font-size: 32px;
	color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
	margin-top: 8px;
	color: #666;
}
</style>
