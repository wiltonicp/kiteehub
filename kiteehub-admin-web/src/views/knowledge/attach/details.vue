<template>
	<xn-form-container :title="record.docName" :width="900" :visible="visible" :destroy-on-close="true" @close="onClose">
		<a-alert :message="`分段预览：（${totalVal}组）`" type="success" show-icon style="margin: 10px 0;">
			<template #icon><smile-outlined /></template>
		</a-alert>
		<div style="background-color: #ececec5f; padding: 10px">
			<a-row :gutter="[16, 16]">
				<a-col :span="8" v-for="(item, index) in recordsList" :key="index">
					<a-card :title="item.id" :bordered="false">
						<template #extra
							><a href="#"
								><a-tag color="blue">{{ index }}#</a-tag></a
							></template
						>

						<p class="chunk">{{ item.content }}</p>
					</a-card>
				</a-col>
			</a-row>
		</div>

		<template #footer>
			<div class="pagination">
				<a-pagination
					v-model:current="current"
					v-model:pageSize="size"
					show-size-changer
					:total="totalVal"
					@showSizeChange="onShowSizeChange"
				/>
			</div>
		</template>
	</xn-form-container>
</template>

<script setup name="knowledgeAttachForm">
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import knowledgeAttachApi from '@/api/knowledge/knowledgeAttachApi'
const size = ref(10)
const current = ref(1)
const totalVal = ref(0)
const recordsList = ref([])
let dataObj = reactive({
	record: {}
})

const onShowSizeChange = (currentVal, pageSize) => {
	console.log(currentVal, pageSize)
	size.value = pageSize
	current.value = currentVal
}
watch(size, () => {
	console.log('size', size.value)
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
		size: size.value
	}
	let { total, records } = await knowledgeAttachApi.knowledAttachChunk(parame)
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
	height: 130px;
	overflow: auto;
}
*::-webkit-scrollbar {
	/*滚动条整体样式*/
	width: 6px; /*高宽分别对应横竖滚动条的尺寸*/
	height: 1px;
}
*::-webkit-scrollbar-thumb {
	/*滚动条里面深色条*/
	border-radius: 10px;
	box-shadow: inset 0 0 5px rgba(236, 236, 236, 0.1);
	background: #ccc;
}
*::-webkit-scrollbar-track {
	/*滚动条里面轨道*/
	box-shadow: inset 0 0 5px rgba(236, 236, 236, 0.1);
	border-radius: 10px;
	background: #ededed;
}
</style>

