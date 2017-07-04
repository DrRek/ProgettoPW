$(document).ready(function() {
	$("input[type=date]").on("keyup", function(e) {
		console.log(e.keyCode);
		len = this.value.length
		if(( len == 5 || len == 8 ) && codeIsNumber(e.keyCode)){
			this.value=this.value.substring(0,len-1)+"-"+this.value.substring(len-1,len)
		} else if( !codeIsNumber(e.keyCode) && !codeIsSeparator(e.keyCode) && !codeIsReturn(e.keyCode)){
			this.value = this.value.substring(0,len-1);
		} else if( len>=11){
			this.value = this.value.substring(0,10);
		}
	});
});

function codeIsNumber(e){
	if(( e >= 96 && e <= 106 ) || ( e >= 48 && e <= 57 )){
		return true
	}
	return false
}

function codeIsSeparator(e){
	if(( e == 109 || e== 173 ))
		return true
	return false
}

function codeIsReturn(e){
	if( e == 8)
		return true
	return false
}