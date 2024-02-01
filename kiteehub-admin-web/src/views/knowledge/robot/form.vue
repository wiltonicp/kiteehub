<template>
	<xn-form-container
		:title="formData.id ? '编辑智能客服' : '增加智能客服'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="客服ID：" name="ID">
				<a-input v-model:value="formData.id" placeholder="请输入客服名称" allow-clear disabled/>
			</a-form-item>
			<a-form-item label="客服名称：" name="name">
				<a-input v-model:value="formData.name" placeholder="请输入客服名称" allow-clear />
			</a-form-item>
			<a-form-item label="描述：" name="description">
				<a-textarea v-model:value="formData.description" placeholder="请输入描述" :auto-size="{ minRows: 3, maxRows: 5 }" />
			</a-form-item>
			<a-divider style="border-color: #1890FF">AI 配置</a-divider>
			<a-form-item label="AI 模型：" name="model">
				<a-select v-model:value="formData.model" placeholder="请选择模型" :options="modelOptions" />
			</a-form-item>
			<a-form-item name="temperature">
				<template #label>
					<a-tooltip>
						<template #title> 温度设在 0.7–0.9 之间是创造性任务最常见的温度！ </template>
						<question-circle-outlined />
					</a-tooltip>
					&nbsp 采样温度：
				</template>
				<a-slider v-model:value="formData.temperature" placeholder="请滑动采样温度" :min="0" :max="2" :step="0.1" style="width: 100%" />
			</a-form-item>
			<a-form-item name="maxTokens">
				<template #label>
					<a-tooltip>
						<template #title> 单条消息恢复最大字数！ </template>
						<question-circle-outlined />
					</a-tooltip>
					&nbsp 回复上限：
				</template>
				<a-slider v-model:value="formData.maxTokens" placeholder="请滑动回复上限" :max="10000" style="width: 100%" />
			</a-form-item>
			<a-form-item name="prompt">
				<template #label>
					<a-tooltip>
						<template #title> 模型固定的引导词，通过调整该内容，可以引导模型聊天方向。该内容会被固定在上下文的开头。可使用变量，例如 {{language}} </template>
						<question-circle-outlined />
					</a-tooltip>
					&nbsp 提示词：
				</template>
				<a-textarea v-model:value="formData.prompt" placeholder="模型固定的引导词，通过调整该内容，可以引导模型聊天方向。该内容会被固定在上下文的开头。可使用变量，例如 {{language}}" :auto-size="{ minRows: 3, maxRows: 5 }" />
			</a-form-item>
			<a-form-item name="prologue">
				<template #label>
					<a-tooltip>
						<template #title> 每次对话开始前，发送一个初始内容。支持标准 Markdown 语法，可使用的额外标记:
							[快捷按键]: 用户点击后可以直接发送该问题 </template>
						<question-circle-outlined />
					</a-tooltip>
					&nbsp 对话开场白：
				</template>
				<a-textarea v-model:value="formData.prologue" placeholder="每次对话开始前，发送一个初始内容。支持标准 Markdown 语法，可使用的额外标记:
[快捷按键]: 用户点击后可以直接发送该问题" :auto-size="{ minRows: 3, maxRows: 5 }" />
			</a-form-item>
			<a-divider style="border-color: #1890FF">关联知识库</a-divider>
			<a-form-item name="knowledge">
				<template #label>
					<a-tooltip>
						<template #title> 仅能选择同一个索引模型的知识库 </template>
						<question-circle-outlined />
					</a-tooltip>
					&nbsp 选择知识库：
				</template>
				<a-select v-model:value="formData.kids" mode="multiple" placeholder="请选择知识库" :options="knowledgeGatherOptions" />
			</a-form-item>
			<a-form-item name="searchParam">
				<template #label>
					<a-tooltip>
						<template #title> 语义检索：使用向量进行文本相关性查询<br/>
							全文检索：使用传统的全文检索，适合查找一些关键词和主谓语特殊的数据<br/>
							混合检索：使用向量检索与全文检索的综合结果返回，使用RRF算法进行排序。
						</template>
						<question-circle-outlined />
					</a-tooltip>
					&nbsp 搜索参数：
				</template>
				<a-select v-model:value="formData.searchParam" placeholder="请选择搜索参数" :options="knowledgeSearchParametersOptions" />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="knowledgeRobotForm">
	import tool from '@/utils/tool'
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import knowledgeRobotApi from '@/api/knowledge/knowledgeRobotApi'
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	const modelOptions = ref([])
	const language = ref(`{{language}}`)
	const knowledgeGatherOptions = ref([])
	const knowledgeSearchParametersOptions = ref([])

	// 打开抽屉
	const onOpen = (record) => {
		visible.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
		}
		modelOptions.value = tool.dictList('AI_MODEL')
		knowledgeGatherOptions.value = tool.dictList('KNOWLEDGE_GATHER')
		knowledgeSearchParametersOptions.value = tool.dictList('knowledge_search_parameters')
	}
	// 关闭抽屉
	const onClose = () => {
		formRef.value.resetFields()
		formData.value = {}
		visible.value = false
	}
	// 默认要校验的
	const formRules = {
		name: [required('请输入客服名称')],
		model: [required('请选择AI 模型')],
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
			const formDataParam = cloneDeep(formData.value)
			knowledgeRobotApi
				.knowledgeRobotSubmitForm(formDataParam, formDataParam.id)
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
