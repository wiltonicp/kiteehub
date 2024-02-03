<template>
	<xn-form-container
		:title="formData.id ? '重命名知识' : '新增导入知识'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="formData.id ? onClose() : addOnClose()"
	>
		<!-- 新增 -->
		<div class="card-container" v-if="!formData.id">
			<a-tabs v-model:activeKey="activeKey" type="card">
				<a-tab-pane key="1" tab="文件上传导入">
					<div style="margin-bottom: 30px">
						<a-upload style="float: left; width: 100%" :fileList="fileList"></a-upload>
					</div>

					<a-upload-dragger
						v-model:fileList="fileList"
						name="file"
						:multiple="true"
						:action="action"
						:headers="headers"
						:progress="progress"
						:data="{ kid: typeUrlItem, areaIds: areaIds }"
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
<!--				<a-tab-pane key="2" tab="网址链接提取">-->
<!--					<a-input v-model:value="value2">-->
<!--						<template #addonBefore>-->
<!--							<a-select v-model:value="url" style="width: 90px">-->
<!--								<a-select-option value="Http://">http://</a-select-option>-->
<!--								<a-select-option value="Https://">https://</a-select-option>-->
<!--							</a-select>-->
<!--						</template>-->
<!--					</a-input>-->
<!--				</a-tab-pane>-->
			</a-tabs>

<!--			<div style="margin-top: 50px">-->
<!--				<a-form-item label="区域选择：" name="areaSelection" v-if="areaList.length > 0">-->
<!--					<a-tree-select-->
<!--						v-model:value="areaIds"-->
<!--						style="width: 100%"-->
<!--						tree-checkable-->
<!--						tree-default-expand-all-->
<!--						:show-checked-strategy="SHOW_PARENT"-->
<!--						:height="233"-->
<!--						:tree-data="areaList"-->
<!--						:max-tag-count="10"-->
<!--						tree-node-filter-prop="name"-->
<!--						:fieldNames="{ children: 'children', label: 'name', value: 'id' }"-->
<!--					>-->
<!--					</a-tree-select>-->
<!--				</a-form-item>-->
<!--			</div>-->
		</div>

		<!-- 重命名 -->
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical" v-else>
			<a-form-item label="知识ID：" name="docId">
				<a-input v-model:value="formData.docId" placeholder="请输入知识ID" allow-clear disabled />
			</a-form-item>
			<a-form-item label="知识名称：" name="docName">
				<a-input v-model:value="formData.docName" placeholder="请输入知识名称" allow-clear />
			</a-form-item>
			<a-form-item label="知识类型：" name="docType">
				<a-input v-model:value="formData.docType" placeholder="请输入知识类型" allow-clear disabled />
			</a-form-item>

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

<!--			<a-form-item label="区域选择：" name="areaSelection" v-if="areaList.length > 0">-->
<!--				<a-tree-select-->
<!--					v-model:value="formData.areaIds"-->
<!--					style="width: 100%"-->
<!--					tree-checkable-->
<!--					tree-default-expand-all-->
<!--					:show-checked-strategy="SHOW_PARENT"-->
<!--					:height="233"-->
<!--					:tree-data="areaList"-->
<!--					:max-tag-count="10"-->
<!--					tree-node-filter-prop="name"-->
<!--					:fieldNames="{ children: 'children', label: 'name', value: 'id' }"-->
<!--				>-->
<!--				</a-tree-select>-->
<!--			</a-form-item>-->
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
import { message, TreeSelect } from 'ant-design-vue'
import knowledgeAttachApi from '@/api/knowledge/knowledgeAttachApi'
const SHOW_PARENT = TreeSelect.SHOW_PARENT

// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null, getParameUrl: null })
const action = ref(`${import.meta.env.VITE_API_BASEURL}/knowledge/attach/add`)
const headers = ref({})
const activeKey = ref('1')
const fileList = ref([])
const url = ref('http://')
const typeUrlItem = ref('')
const formRef = ref()
const areaList = ref([])

const areaIds = ref([])

// 表单数据
const formData = ref({})
const submitLoading = ref(false)
const gatherStateOptions = ref([])

onMounted(() => {
	getToken()
	// getArea()
})
// 获取token
const getToken = () => {
	const token = tool.data.get('TOKEN')
	headers.value[sysConfig.TOKEN_NAME] = sysConfig.TOKEN_PREFIX + token
}
// 获取字典区域
const getArea = () => {
	const DICT_TYPE_TREE_DATA = tool.data.get('DICT_TYPE_TREE_DATA')
	if (DICT_TYPE_TREE_DATA) {
		areaList.value = DICT_TYPE_TREE_DATA.find((item) => item.dictValue === 'AREA').children
	}
}
// 打开抽屉
const onOpen = (typeUrl, record) => {
	visible.value = true
	fileList.value = []
	typeUrlItem.value = typeUrl
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
	}
	gatherStateOptions.value = tool.dictList('Gather')
}
//区域id
let getAreaIds = (value) => {
	areaIds.value = value
	console.log(areaIds.value, 'areaIds')
}
// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	formData.value = {}
	visible.value = false
}
// 新增导入关闭抽屉
const addOnClose = () => {
	if (submitLoading.value) return
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
	submitLoading.value = true
	const status = info.file.status
	if (status !== 'uploading') {
		console.log(info.file, info.fileList)
	}
	if (status === 'done') {
		message.success(`${info.file.name} 上传成功.`)
	} else if (status === 'error') {
		message.error(`${info.file.name} 上传失败.`)
	}

	// info参数包含了上传信息，如文件列表、错误信息等
	const fileList = [...info.fileList]

	if (fileList.every((item) => item.status === 'done')) {
		console.log('所有文件都已上传')
		submitLoading.value = false
		// 此处可以编写其他业务逻辑
	} else {
		console.log('还有未上传的文件')
	}
}

const progress = {
	strokeColor: {
		'0%': '#108ee9',
		'100%': '#87d068'
	},
	strokeWidth: 5,
	format: (percent) => `${parseFloat(percent.toFixed(2))}%`,
	class: 'test'
}

const handleDrop = (e) => {
	console.log(e)
}
const confirm = () => {
	submitLoading.value = false
	visible.value = false
	emit('successful')
}

watch(formData.areaIds, () => {
	console.log('areaIds', formData.areaIds)
})

// 抛出函数
defineExpose({
	onOpen,
	getAreaIds
})
</script>
