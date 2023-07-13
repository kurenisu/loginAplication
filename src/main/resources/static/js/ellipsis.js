window.onload = function(){
	const applyTextLimit = () => {
  		let maxLength = 50; //上限文字数
  		let limitedText = document.getElementById('limited-text');
  		let originalText = limitedText.innerText;
  		if (originalText.length > maxLength) {
    		limitedText.innerText = originalText.substr(0, maxLength) + '...';
  		}
	}
	applyTextLimit();
}