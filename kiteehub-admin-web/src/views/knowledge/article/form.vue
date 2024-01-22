<template>
	<xn-form-container
		:title="formData.id ? '编辑热门动态' : '增加热门动态'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="知识类别：" name="kid">
				<a-select v-model:value="formData.kid" placeholder="请选择知识类别" :options="kidOptions" />
			</a-form-item>
			<a-form-item label="标题：" name="title">
				<a-input v-model:value="formData.title" placeholder="请输入标题" allow-clear />
			</a-form-item>
			<a-form-item label="封面：" name="headImg">
				<a-upload
					v-model:file-list="fileList"
					name="avatar"
					list-type="picture-card"
					class="avatar-uploader"
					:show-upload-list="false"
					:action="action"
					:headers="headers"
					:before-upload="beforeUpload"
					@change="handleChange"
				>
					<img v-if="imageUrl" :src="imageUrl" alt="avatar" />
					<div v-else>
						<loading-outlined v-if="loading"></loading-outlined>
						<plus-outlined v-else></plus-outlined>
						<div class="ant-upload-text">上传</div>
					</div>
				</a-upload>
			</a-form-item>
			<a-form-item label="区域选择：" name="areaIds" v-if="areaList.length > 0">
				<a-tree-select
					v-model:value="formData.areaIds"
					style="width: 100%"
					tree-checkable
					tree-default-expand-all
					:show-checked-strategy="SHOW_PARENT"
					:height="233"
					:tree-data="areaList"
					:max-tag-count="10"
					tree-node-filter-prop="name"
					:fieldNames="{ children: 'children', label: 'name', value: 'id' }"
				>
				</a-tree-select>
			</a-form-item>

			<a-form-item label="发送方式：" name="sendType">
				<a-radio-group v-model:value="sendType">
					<a-radio value="TXT">纯文本</a-radio>
					<a-radio value="HTML">HTML</a-radio>
				</a-radio-group>
			</a-form-item>
			<a-form-item label="正文" name="content" v-if="sendType === 'TXT'">
				<a-textarea v-model:value="formData.content" placeholder="请输入正文" :auto-size="{ minRows: 6, maxRows: 6 }" />
			</a-form-item>
			<a-form-item label="正文" name="content" v-if="sendType === 'HTML'">
				<xn-editor v-model="formData.content" placeholder="请输入正文" :height="200"></xn-editor>
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="knowledgeHotArticleForm">
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import { message } from 'ant-design-vue'
import sysConfig from '@/config/index'
import knowledgeHotArticleApi from '@/api/knowledge/knowledgeHotArticleApi'
import XnEditor from '@/components/Editor/index.vue'

const fileList = ref([])
const loading = ref(false)
const imageUrl = ref('')

// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
const kidOptions = ref([])
// 发送文本方式
const sendType = ref('TXT')
const areaList = ref([])
const action = ref(`${import.meta.env.VITE_API_BASEURL}/knowledge/article/add`)
const headers = ref({})
onMounted(() => {
	getToken()
	getArea()
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
		// Get this url from response in real world.
		// getBase64(info.file.originFileObj, (base64Url) => {
		// 	imageUrl.value = base64Url
		// 	loading.value = false
		// })
	}
	if (info.file.status === 'error') {
		loading.value = false
		message.error('upload error')
	}
}
const beforeUpload = (file) => {
	const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
	if (!isJpgOrPng) {
		message.error('You can only upload JPG file!')
	}
	const isLt2M = file.size / 1024 / 1024 < 2
	if (!isLt2M) {
		message.error('Image must smaller than 2MB!')
	}
	return isJpgOrPng && isLt2M
}

// 获取字典区域
const getArea = () => {
	const DICT_TYPE_TREE_DATA = tool.data.get('DICT_TYPE_TREE_DATA')
	areaList.value = DICT_TYPE_TREE_DATA.find((item) => item.dictValue === 'AREA').children
	console.log(areaList.value, 'areaList')
}
// 打开抽屉
const onOpen = (record) => {
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
	}
	kidOptions.value = tool.dictList('KNOWLEDGE_GATHER')
}
// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	formData.value = {}
	visible.value = false
}
// 默认要校验的
const formRules = {
	kid: [required('请选择知识类别')],
	title: [required('请输入标题')],
	areaIds: [required('请选择区域')]
}
// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		submitLoading.value = true
		const formDataParam = cloneDeep(formData.value)
		knowledgeHotArticleApi
			.knowledgeHotArticleSubmitForm(formDataParam, formDataParam.id)
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
