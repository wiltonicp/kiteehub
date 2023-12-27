<template>
	<xn-form-container
		:title="formData.id ? '重命名文件' : '新增文件'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
<!--			<a-form-item label="知识库ID：" name="kid">-->
<!--				<a-input v-model:value="formData.kid" placeholder="请输入知识库ID" allow-clear />-->
<!--			</a-form-item>-->
<!--			<a-form-item label="文档ID：" name="docId">-->
<!--				<a-input v-model:value="formData.docId" placeholder="请输入文档ID" allow-clear />-->
<!--			</a-form-item>-->
			<a-form-item label="文档名称：" name="docName">
				<a-input v-model:value="formData.docName" placeholder="请输入文档名称" allow-clear />
			</a-form-item>
			<a-form-item label="文档类型：" name="docType">
				<a-input v-model:value="formData.docType" placeholder="请输入文档类型" allow-clear disabled />
			</a-form-item>
<!--			<a-form-item label="文档内容：" name="content">-->
<!--				<a-input v-model:value="formData.content" placeholder="请输入文档内容" allow-clear />-->
<!--			</a-form-item>-->
			<a-form-item label="数据总量：" name="totalData">
				<a-input v-model:value="formData.totalData" placeholder="请输入数据总量" allow-clear disabled />
			</a-form-item>
			<a-form-item label="状态：" name="gatherState">
				<a-select v-model:value="formData.gatherState" placeholder="请选择状态" :options="gatherStateOptions" disabled />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="knowledgeAttachForm">
	import tool from '@/utils/tool'
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import knowledgeAttachApi from '@/api/knowledge/knowledgeAttachApi'
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	const gatherStateOptions = ref([])

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
	// 默认要校验的
	const formRules = {
	}
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
	// 抛出函数
	defineExpose({
		onOpen
	})
</script>
