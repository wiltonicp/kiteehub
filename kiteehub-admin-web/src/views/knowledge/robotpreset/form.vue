<template>
	<xn-form-container
		:title="formData.id ? '编辑预设问答' : '增加预设问答'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="客服：" name="robotId">
				<a-select v-model:value="formData.robotId" placeholder="请选择客服" :options="robotIdOptions" />
			</a-form-item>
			<a-form-item label="问题：" name="question">
				<a-textarea v-model:value="formData.question" placeholder="请输入问题" :auto-size="{ minRows: 3, maxRows: 5 }" />
			</a-form-item>
			<a-form-item label="答案：" name="answer">
				<a-textarea v-model:value="formData.answer" placeholder="请输入答案" :auto-size="{ minRows: 3, maxRows: 5 }" />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="knowledgeRobotPresetForm">

	import tool from '@/utils/tool'
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import knowledgeRobotPresetApi from '@/api/knowledge/knowledgeRobotPresetApi'
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
  const robotIdOptions = ref([])

	// 打开抽屉
	const onOpen = (record,arr) => {
		visible.value = true
    robotIdOptions.value = arr
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
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
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
			const formDataParam = cloneDeep(formData.value)
			knowledgeRobotPresetApi
				.knowledgeRobotPresetSubmitForm(formDataParam, formDataParam.id)
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
