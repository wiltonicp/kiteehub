<template>
	<xn-form-container
		:title="formData.id ? '编辑通知公告' : '增加通知公告'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
            <a-form-item label="人员类型" name="personnelType">
                <a-radio-group v-model:value="formData.personnelType" placeholder="请选择人员类型" :options="personnelTypeOptions" />
            </a-form-item>
			<a-form-item label="主题：" name="subject">
				<a-input v-model:value="formData.subject" placeholder="请输入主题" allow-clear />
			</a-form-item>
			<a-form-item label="文本方式：" name="sendType">
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
			<a-form-item label="扩展信息：" name="extJson">
				<a-textarea v-model:value="formData.extJson" placeholder="请输入扩展信息" :auto-size="{ minRows: 3, maxRows: 5 }" />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="knowledgeNoticeForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import knowledgeNoticeApi from '@/api/knowledge/knowledgeNoticeApi'
	import XnEditor from "@/components/Editor/index.vue";
    import tool from "@/utils/tool";
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
    const personnelTypeOptions = ref([])
	// 文本方式
	const sendType = ref('TXT')

	// 打开抽屉
	const onOpen = (record) => {
		visible.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
		}
        personnelTypeOptions.value = tool.dictList('PERSONNEL_TYPE')
	}
	// 关闭抽屉
	const onClose = () => {
		formRef.value.resetFields()
		formData.value = {}
		visible.value = false
	}
	// 默认要校验的
	const formRules = {
        personnelType: [required('请选择人员类型')],
		subject: [required('请输入主题')],
		content: [required('请输入正文')],
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
			const formDataParam = cloneDeep(formData.value)
			knowledgeNoticeApi
				.knowledgeNoticeSubmitForm(formDataParam, formDataParam.id)
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
