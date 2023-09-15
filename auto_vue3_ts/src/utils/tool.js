// 重写setItem事件，当使用setItem的时候，触发，window.dispatchEvent派发事件
function dispatchEventStroage () {
  const signSetItem = localStorage.setItem
  localStorage.setItem = function (key, val) {
    const setEvent = new Event('setItemEvent')
    setEvent.key = key
    setEvent.newValue = val
    window.dispatchEvent(setEvent)
    signSetItem.apply(this, arguments)
  }
}

export default dispatchEventStroage
