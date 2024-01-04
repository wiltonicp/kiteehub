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
			<a-form-item label="发送方式：" name="sendType">
				<a-radio-group v-model:value="sendType">
					<a-radio value="TXT">纯文本</a-radio>
					<a-radio value="HTML">HTML</a-radio>
				</a-radio-group>
			</a-form-item>
			<a-form-item label="正文" name="content" v-if="sendType === 'TXT'">
				<a-textarea
					v-model:value="formData.content"
					placeholder="请输入正文"
					:auto-size="{ minRows: 6, maxRows: 6 }"
				/>
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
	import knowledgeHotArticleApi from '@/api/knowledge/knowledgeHotArticleApi'
	import XnEditor from "@/components/Editor/index.vue";
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
