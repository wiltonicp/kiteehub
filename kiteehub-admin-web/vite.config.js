/*
 * Copyright [2022] [https://www.kiteehub.com]
 *
 * Kiteehub是内部代码，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Kiteehub源码头部的版权声明。
 * 3.本项目代码使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.kiteehub.com
 * 5.本项目只可用于内部开发，如有问题可联系团队wilton.icp@gmail.com商议合作。
 */
import { resolve } from 'path'
import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import Components from 'unplugin-vue-components/vite'
import VueJSX from '@vitejs/plugin-vue-jsx'
import AutoImport from 'unplugin-auto-import/vite'
import vueSetupExtend from 'vite-plugin-vue-setup-extend'
import { visualizer } from 'rollup-plugin-visualizer'
import Less2CssVariablePlugin from 'antd-less-to-css-variable'
import viteCompression from 'vite-plugin-compression'

export const r = (...args) => resolve(__dirname, '.', ...args)

const removeModulePreloadPlugin = (keys) => {
	if (!keys || !keys.length) {
		return
	}
	return {
		name: 'remove-module-preload',
		transformIndexHtml: {
			enforce: 'after',
			transform(html, ctx) {
				let result = html
				keys.forEach((key) => {
					result = result.replace(new RegExp(`<link rel="modulepreload"?.*${key}?.*`), '')
				})
				return result
			}
		}
	}
}

export default defineConfig(({ command, mode }) => {
	const envConfig = loadEnv(mode, './')

	const alias = {
		'~': `${resolve(__dirname, './')}`,
		'@/': `${resolve(__dirname, 'src')}/`
	}

	return {
		server: {
			port: envConfig.VITE_PORT,
			proxy: {
				'/api': {
					target: envConfig.VITE_API_BASEURL,
					ws: false,
					changeOrigin: true,
					rewrite: (path) => path.replace(/^\/api/, '')
				}
			}
		},
		resolve: {
			alias
		},
		// 解决警告You are running the esm-bundler build of vue-i18n.
		define: {
			__VUE_I18N_FULL_INSTALL__: true,
			__VUE_I18N_LEGACY_API__: true,
			__VUE_I18N_PROD_DEVTOOLS__: true
		},
		build: {
			// sourcemap: true,
			manifest: true,
			brotliSize: false,
			rollupOptions: {
				output: {
					manualChunks: {
						echarts: ['echarts'],
						'ant-design-vue': ['ant-design-vue'],
						vue: ['vue', 'vue-router', 'pinia', 'vue-i18n']
					}
				}
			},
			chunkSizeWarningLimit: 1000
		},
		plugins: [
			vue({
				script: {
					refTransform: true
				}
			}),
			viteCompression(),
			vueSetupExtend(),
			VueJSX(),
			AutoImport({
				imports: ['vue'],
				dirs: ['./src/utils/permission'],
				dts: r('src/auto-imports.d.ts')
			}),
			// 组件按需引入
			Components({
				dirs: [r('src/components')],
				dts: false,
				resolvers: []
			}),
			visualizer()
		],
		css: {
			preprocessorOptions: {
				less: {
					javascriptEnabled: true,
					plugins: [new Less2CssVariablePlugin()]
				}
			}
		},
		optimizeDeps: {}
	}
})
