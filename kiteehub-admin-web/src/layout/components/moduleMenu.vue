<template>
	<div class="layout-items-center" v-if="moduleUnfoldOpen">
		<a-menu
			v-model:selectedKeys="selectedKeys"
			mode="horizontal"
			v-if="menu && menu.length > 1"
			class="module-menu"
			id="moduleMunu"
		>
			<a-menu-item
				v-for="item in menu"
				:key="item.id"
				class="!px-3"
				style="position: relative"
				@click="moduleClick(item.id)"
			>
				<template #icon>
					<component :is="item.meta.icon" />
				</template>
				<span style="margin-left: -5px">{{ item.meta.title }}</span>
			</a-menu-item>
		</a-menu>
	</div>
	<div v-else  class="panel-item hidden-sm-and-down">
		<a-popover v-if="menu.length > 1" placement="bottomLeft">
			<template #content>
				<a-row :gutter="[0, 5]" class="module-row">
					<div v-for="item in menu" :key="item.id">
						<a-col :span="6">
							<a-tag class="module-card" :color="item.color" @click="moduleClick(item.id)">
								<component :is="item.meta.icon" class="module-card-icon" />
								<div class="module-card-font">{{ item.meta.title }}</div>
							</a-tag>
						</a-col>
					</div>
				</a-row>
			</template>
			<appstore-outlined />
		</a-popover>
	</div>
</template>

<script setup>
	import router from '@/router'
	import tool from '@/utils/tool'
	import { globalStore } from '@/store'
	import { watch } from 'vue'
	import { storeToRefs } from 'pinia'

	const store = globalStore()

	const { moduleUnfoldOpen, topHanderThemeColorOpen } = storeToRefs(store)
	const moduleBackColor = ref(topHanderThemeColorOpen)

	// 监听目录是否折叠
	watch(moduleUnfoldOpen, (newValue) => {
		nextTick(() => {
			setModuleBackColor()
		})
	})
	// 监听是否开启了顶栏颜色
	watch(topHanderThemeColorOpen, (newValue) => {
		moduleBackColor.value = newValue
		setModuleBackColor()
	})

	const emit = defineEmits({ switchModule: null })
	const menu = router.getMenu()
	const selectedKeys = ref([tool.data.get('KITEE_MENU_MODULE_ID')])
	const moduleClick = (id) => {
		emit('switchModule', id)
		tool.data.set('KITEE_MENU_MODULE_ID', id)
		nextTick(() => {
			setSelectedKeys()
		})
	}

	onMounted(() => {
		setModuleBackColor()
	})
	// 设置背景色
	const setModuleBackColor = () => {
		if (moduleUnfoldOpen.value) {
			try {
				const moduleMunu = document.getElementById('moduleMunu')
				moduleBackColor.value
					? moduleMunu.classList.add('module-menu-color')
					: moduleMunu.classList.remove('module-menu-color')
			} catch (err) {}
			setSelectedKeys()
		}
	}
	// 设置选中
	const setSelectedKeys = () => {
		// 顶部应用列表让显示出来默认的，不这么实现不会显示的，相信老俞
		moduleBackColor.value
			? (selectedKeys.value = new Array([]))
			: (selectedKeys.value = [tool.data.get('KITEE_MENU_MODULE_ID')])
	}
</script>

<style type="less">
	.module-row {
		max-width: 357px;
	}
	.module-card {
		width: 80px;
		height: 80px;
		background-color: #0d84ff;
		text-align: center;
		align-items: center;
		cursor: pointer;
		border-radius: 2px;
	}
	.module-card-icon {
		color: white;
		font-size: 20px;
		margin-top: 20px;
	}
	.module-card-font {
		color: white;
		font-size: 8px;
	}
	.ant-menu-horizontal > .ant-menu-item::after,
	.ant-menu-horizontal > .ant-menu-submenu::after {
		content: none;
	}
	.module-menu {
		line-height: 50px;
		border-bottom: 0px;
		width: 105%;
		flex: 0 0 auto;
	}
	.module-menu-color {
		color: white;
		background-color: var(--primary-color);
	}
</style>
