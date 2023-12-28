<template>
	<xn-form-container :title="record.docName" :width="1400" :visible="visible" :destroy-on-close="true" @close="onClose">
		<div style="background-color: #ececec5f; padding: 10px">
			<a-row :gutter="[16, 16]">
				<a-col :span="8" v-for="(item, index) in recordsList" :key="index">
					<a-card title="Card title" :bordered="false">
						<template #extra
							><a href="#"
								><a-tag color="blue">{{ index }}#</a-tag></a
							></template
						>

						<p class="chunk">card content</p>
					</a-card>
				</a-col>
			</a-row>
		</div>

		<a-pagination
			v-model:current="current"
			v-model:pageSize="pageSize"
			show-size-changer
			:total="totalVal"
			@showSizeChange="onShowSizeChange"
		/>
	</xn-form-container>
</template>

<script setup name="knowledgeAttachForm">
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import knowledgeAttachApi from '@/api/knowledge/knowledgeAttachApi'
const pageSize = ref(10)
const current = ref(1)
const totalVal = ref(0)
const recordsList = ref([])
let dataObj = reactive({
	record: {}
})

const onShowSizeChange = (current, pageSize) => {
	console.log(current, pageSize)
	pageSize.value = pageSize
	current.value = current
}
watch(pageSize, () => {
	console.log('pageSize', pageSize.value)
	knowledAttachChunk()
})
watch(current, () => {
	console.log('current', current.value)
	knowledAttachChunk()
})
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })

// 打开抽屉
const onOpen = (record) => {
	console.log('打开抽屉', record)
	dataObj.record = record
	visible.value = true
	knowledAttachChunk()
}
// 关闭抽屉
const onClose = () => {
	visible.value = false
}
// 默认要校验的
const formRules = {}
// 验证并提交数据
const onSubmit = () => {}

const knowledAttachChunk = async () => {
	let parame = {
		id: dataObj.record.id,
		current: current.value,
		size: pageSize.value
	}
	console.log('parame', parame)
	let {
        total,
        records
    } = await knowledgeAttachApi.knowledAttachChunk(parame)
    console.log('records', records)
	totalVal.value = total
	recordsList.value = records

}

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

