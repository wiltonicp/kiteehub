<template>
	<xn-form-container
		:title="formData.id ? '编辑知识库' : '增加知识库'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="KID：" name="kid">
				<a-input v-model:value="formData.kid" placeholder="系统自动生成" allow-clear disabled />
			</a-form-item>
			<a-form-item label="名称：" name="kname">
				<a-input v-model:value="formData.kname" placeholder="请输入名称" allow-clear />
			</a-form-item>
      <a-form-item label="官网：" name="kname">
        <a-input v-model:value="formData.website" placeholder="请输入官网地址" allow-clear />
      </a-form-item>
<!--			<a-form-item label="区域：" name="area">-->
<!--				<a-input v-model:value="formData.area" placeholder="请输入区域" allow-clear />-->
<!--			</a-form-item>-->
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="knowledgeForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import knowledgeApi from '@/api/knowledge/knowledgeApi'
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)

	// 打开抽屉
	const onOpen = (record) => {
		visible.value = true
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
			knowledgeApi
				.knowledgeSubmitForm(formDataParam, formDataParam.id)
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
