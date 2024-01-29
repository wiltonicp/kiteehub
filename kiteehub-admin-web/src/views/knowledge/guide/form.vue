<template>
	<xn-form-container
		:title="formData.id ? '编辑业务指南' : '增加业务指南'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="分类：" name="category">
				<a-select v-model:value="formData.category" placeholder="请选择分类" :options="categoryOptions" />
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
			<a-form-item label="办理材料：" name="handleMaterial">
				<a-textarea v-model:value="formData.handleMaterial" placeholder="请输入办理材料" :auto-size="{ minRows: 3, maxRows: 5 }" />
			</a-form-item>
			<a-form-item label="办理时限：" name="handleTime">
				<a-input v-model:value="formData.handleTime" placeholder="请输入办理时限" allow-clear />
			</a-form-item>
			<a-form-item label="流程图：" name="flowChart">
				<a-input v-model:value="formData.flowChart" placeholder="请输入流程图" allow-clear />
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
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	const categoryOptions = ref([])

	// 打开抽屉
	const onOpen = (record) => {
		visible.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
		}
		categoryOptions.value = tool.dictList('OPERATION_FLOW')
	}
	// 关闭抽屉
	const onClose = () => {
		formRef.value.resetFields()
		formData.value = {}
		visible.value = false
	}
	// 默认要校验的
	const formRules = {
		category: [required('请输入分类')],
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
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
