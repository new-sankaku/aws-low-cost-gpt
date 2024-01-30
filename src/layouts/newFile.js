/* __placeholder__ */
export default (await import('vue')).defineComponent({
data() {
return {
layers: [
'Layer 1234567891',
'Layer 2',
'Layer 3',
'Layer 2',
'Layer 3',
'Layer 2',
'Layer 3',
'Layer 2',
'Layer 3',
'Layer 2',
'Layer 3',
'Layer 2',
'Layer 3',
'Layer 2',
'Layer 3',
'Layer 2',
'Layer 3',
],
chatHistory: [],
inputFields: [],
activeLayer: 0,
};
},
methods: {
adjustTextareaHeight(index) {
this.$nextTick(() => {
const textarea = this.$refs[`input${index}`];
if (textarea) {
textarea.style.height = 'auto';
textarea.style.height = textarea.scrollHeight + 'px';
}
});
},
createLayers() {
this.layers.forEach((_, index) => {
this.chatHistory.push([]);
this.inputFields.push('');
});
},
showLayer(index) {
this.activeLayer = index;
this.adjustTextareaHeight(index);
},
sendMessage(index) {
const inputField = this.inputFields[index];
if (inputField.trim() === '') return;

this.chatHistory[index].push({ text: inputField, sender: 'user' });
this.inputFields[index] = '';
this.adjustTextareaHeight(index);
},
},
mounted() {
this.createLayers();
this.showLayer(0);
},
computed: {
chatHistoryHeight() {
return (index) => {
const chatHistory = this.$refs[`chat-history${index}`];
const inputArea = this.$refs[`input${index}`].$el.parentElement;
return `calc(90vh - ${inputArea.offsetHeight}px)`;
};
},
},
});
