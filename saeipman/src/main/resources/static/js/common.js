// 날짜 포맷 변환

// 년도 ex) 2024
function dateToYear(date){
	return date.getFullYear();	
}

// 월
function dateToM(date){
	return date.getMonth() + 1;
}

// 년월 ex) 2024-09
function dateToYM(date){
	return date.getFullYear() + '-' + ((date.getMonth() + 1)+'').padStart(2, '0');
}

// 년월일 ex) 2024-09-01
function dateToYMD(date){
	return date.getFullYear() + '-' + ((date.getMonth() + 1)+'').padStart(2, '0') + '-' + (date.getDate()+'').padStart(2, '0');
}

// form 데이터 직렬화
function frmToJson(frm){
	let formObj = $(frm).serializeArray();
}