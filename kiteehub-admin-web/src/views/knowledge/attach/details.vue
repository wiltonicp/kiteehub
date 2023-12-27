<template>
	<xn-form-container :title="record.docName" :width="1400" :visible="visible" :destroy-on-close="true" @close="onClose">
		<div style="background-color: #ececec5f; padding: 10px">
			<a-row :gutter="[16,16]">
				<a-col :span="8" v-for="(item, index) in 10" :key="index">
					<a-card title="Card title" :bordered="false">
						<template #extra><a href="#"><a-tag color="blue">{{ index }}#</a-tag></a></template>
						
						<p class="chunk">card content</p>
					</a-card>
				</a-col>
			</a-row>
		</div>
	</xn-form-container>
</template>

<script setup name="knowledgeAttachForm">
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import knowledgeAttachApi from '@/api/knowledge/knowledgeAttachApi'

let dataObj = reactive({
	record: {}
})
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })

// 打开抽屉
const onOpen = (record) => {
	console.log('打开抽屉', record)
	dataObj.record = record
	visible.value = true
}
// 关闭抽屉
const onClose = () => {
	visible.value = false
}
// 默认要校验的
const formRules = {}
// 验证并提交数据
const onSubmit = () => {}

const { record } = toRefs(dataObj)

// 抛出函数
defineExpose({
	onOpen
})
</script>

<style  scoped>
.chunk {
	height: 120px;
	overflow: auto;
}
</style>

