<template>
	<a-spin :spinning="loadSpinning">
		<a-form
			ref="formRef"
			:model="formData"
			:rules="formRules"
			layout="vertical"
			:label-col="{ ...layout.labelCol, offset: 0 }"
			:wrapper-col="{ ...layout.wrapperCol, offset: 0 }"
		>
			<a-form-item label="发送邮箱号：" name="KITEE_EMAIL_LOCAL_FROM">
				<a-input v-model:value="formData.KITEE_EMAIL_LOCAL_FROM" placeholder="请输入发送邮箱号" />
			</a-form-item>
			<a-form-item label="邮箱密钥：" name="KITEE_EMAIL_LOCAL_PASSWORD">
				<a-input v-model:value="formData.KITEE_EMAIL_LOCAL_PASSWORD" placeholder="请输入邮箱密钥" />
			</a-form-item>
			<a-form-item>
				<a-button type="primary" :loading="submitLoading" @click="onSubmit()">保存</a-button>
				<a-button style="margin-left: 10px" @click="() => formRef.resetFields()">重置</a-button>
			</a-form-item>
		</a-form>
	</a-spin>
</template>

<script setup name="localEmailForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import { message } from 'ant-design-vue'
	import configApi from '@/api/dev/configApi'

	const formRef = ref()
	const formData = ref({})
	const submitLoading = ref(false)
	const loadSpinning = ref(true)

	// 查询此界面的配置项,并转为表单
	const param = {
		category: 'EMAIL_LOCAL'
	}
	configApi.configList(param).then((data) => {
		loadSpinning.value = false
		if (data) {
			data.forEach((item) => {
				formData.value[item.configKey] = item.configValue
			})
		} else {
			message.warning('表单项不存在，请初始化数据库')
		}
	})

	// 默认要校验的
	const formRules = {
		KITEE_EMAIL_LOCAL_FROM: [required('请输入发送邮箱号')],
		KITEE_EMAIL_LOCAL_PASSWORD: [required('请输入邮箱密钥')]
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
			let submitParam = cloneDeep(formData.value)
			const param = Object.entries(submitParam).map((item) => {
				return {
					configKey: item[0],
					configValue: item[1]
				}
			})
			configApi
				.configEditForm(param)
				.then(() => {})
				.finally(() => {
					submitLoading.value = false
				})
		})
	}
	const layout = {
		labelCol: {
			span: 4
		},
		wrapperCol: {
			span: 12
		}
	}
</script>
