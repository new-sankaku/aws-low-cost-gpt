import { boot } from 'quasar/wrappers'
import { createI18n } from 'vue-i18n'
import messages from 'src/i18n'

import enUs from 'src/i18n/en-us'
import jaJp from 'src/i18n/ja-JP'

const i18n = createI18n({
  locale: 'en-US',
  fallbackLocale: 'en-US',
  messages: {
    'en-US': enUs,
    'ja-JP': jaJp
  }
})

export default ({ app }) => {
  app.use(i18n)
}
