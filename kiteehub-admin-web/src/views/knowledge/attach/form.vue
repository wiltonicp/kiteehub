<template>
	<xn-form-container
		:title="formData.id ? '重命名知识' : '新增导入知识'"
		:width="500"
		:visible="visible"
		:destroy-on-close="true"
		@close="formData.id ? onClose() : addOnClose()"
	>
		<!-- 新增 -->
		<div class="card-container" v-if="!formData.id">
			<a-tabs v-model:activeKey="activeKey" type="card">
				<a-tab-pane key="1" tab="文件上传导入">
					<a-upload-dragger
						v-model:fileList="fileList"
						name="file"
						:multiple="true"
						:action="action"
						:headers="headers"
						:data="{ kid: '5k6ithwioi' }"
						@change="handleChange"
						@drop="handleDrop"
					>
						<p class="ant-upload-drag-icon">
							<inbox-outlined></inbox-outlined>
						</p>
						<p class="ant-upload-text">单击或拖动文件到此区域进行上传</p>
						<p class="ant-upload-hint">支持.txt.docx.pdf.md,.html文件。</p>
					</a-upload-dragger>
				</a-tab-pane>
				<a-tab-pane key="2" tab="网址链接提取">
					<a-input v-model:value="value2">
						<template #addonBefore>
							<a-select v-model:value="url" style="width: 90px">
								<a-select-option value="Http://">http://</a-select-option>
								<a-select-option value="Https://">https://</a-select-option>
							</a-select>
						</template>
					</a-input>
				</a-tab-pane>
			</a-tabs>
		</div>

		<!-- 重命名 -->
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical" v-else>
<!--			<a-form-item label="知识库ID：" name="kid">-->
<!--				<a-input v-model:value="formData.kid" placeholder="请输入知识库ID" allow-clear disabled />-->
<!--			</a-form-item>-->
			<a-form-item label="知识ID：" name="docId">
				<a-input v-model:value="formData.docId" placeholder="请输入知识ID" allow-clear disabled />
			</a-form-item>

			<a-form-item label="知识名称：" name="docName">
				<a-input v-model:value="formData.docName" placeholder="请输入知识名称" allow-clear />
			</a-form-item>
			<a-form-item label="知识类型：" name="docType">
				<a-input v-model:value="formData.docType" placeholder="请输入知识类型" allow-clear disabled />
			</a-form-item>

			<!-- <a-form-item label="文档内容：" name="content">
				<a-input v-model:value="formData.content" placeholder="请输入文档内容" allow-clear />
			</a-form-item> -->

			<a-form-item label="数据总量：" name="totalData">
				<a-input v-model:value="formData.totalData" placeholder="请输入数据总量" allow-clear disabled />
			</a-form-item>
      <a-form-item label="索引模型：" name="indexModel">
        <a-input v-model:value="formData.indexModel" allow-clear disabled />
      </a-form-item>
      <a-form-item label="知识处理模型：" name="fileModel">
        <a-input v-model:value="formData.fileModel" allow-clear disabled />
      </a-form-item>
			<a-form-item label="状态：" name="gatherState">
				<a-select
					v-model:value="formData.gatherState"
					placeholder="请选择状态"
					:options="gatherStateOptions"
					disabled
				/>
			</a-form-item>
		</a-form>
		<template #footer>
			<div v-if="!formData.id">
				<a-button type="primary" @click="confirm" :loading="submitLoading">确定</a-button>
			</div>
			<div v-else>
				<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
				<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
			</div>
		</template>
	</xn-form-container>
</template>

<script setup name="knowledgeAttachForm">
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import sysConfig from '@/config/index'
import { message } from 'ant-design-vue';
import knowledgeAttachApi from '@/api/knowledge/knowledgeAttachApi'

// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const action = ref(`${import.meta.env.VITE_API_BASEURL}/knowledge/attach/add`)
const headers = ref({})
const activeKey = ref('1')
const fileList = ref([])
const url = ref('http://')

const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
const gatherStateOptions = ref([])

onMounted(() => {
	console.log('123123',import.meta.env.VITE_API_BASEURL)
	const token = tool.data.get('TOKEN')
	headers.value[sysConfig.TOKEN_NAME] = sysConfig.TOKEN_PREFIX + token
	console.log(headers.value, '111')
})

// 打开抽屉
const onOpen = (record) => {
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
	}
	gatherStateOptions.value = tool.dictList('Gather')
}
// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	formData.value = {}
	visible.value = false
}
// 新增导入关闭抽屉
const addOnClose = () => {
	visible.value = false
}
// 默认要校验的
const formRules = {}
// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		submitLoading.value = true
		const formDataParam = cloneDeep(formData.value)
		knowledgeAttachApi
			.knowledgeAttachSubmitForm(formDataParam, formDataParam.id)
			.then(() => {
				onClose()
				emit('successful')
			})
			.finally(() => {
				submitLoading.value = false
			})
	})
}
// 上传
const handleChange = (info) => {
	const status = info.file.status
	console.log(info, '123')
	if (status !== 'uploading') {
		console.log(info.file, info.fileList)
	}
	if (status === 'done') {
		message.success(`${info.file.name} file uploaded successfully.`)
	} else if (status === 'error') {
		message.error(`${info.file.name} file upload failed.`)
	}
}
const handleDrop = (e) => {
	console.log(e)
}
const confirm = () => {
	submitLoading.value = false
	visible.value = false
}

// 抛出函数
defineExpose({
	onOpen
})
</script>
