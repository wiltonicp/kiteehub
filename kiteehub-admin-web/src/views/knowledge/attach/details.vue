<template>
	<xn-form-container
		:title="'新增文件'"
		:width="1300"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
	21
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
	

	// 打开抽屉
	const onOpen = (record) => {
        console.log('打开抽屉')
		visible.value = true
	}
	// 关闭抽屉
	const onClose = () => {
		visible.value = false
	}
	// 默认要校验的
	const formRules = {
	}
	// 验证并提交数据
	const onSubmit = () => {

	}
	// 抛出函数
	defineExpose({
		onOpen
	})
</script>
