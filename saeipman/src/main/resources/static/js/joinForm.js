/**
 * joinForm.js
 */
let jsValid = false; // 유효성 체크

// 이름 유효성
let $nameInput = $("#name");
$nameInput.focus();
$nameInput.on("change", (e) =>{
	console.log(e.target.value, "이름");
	if(!e.target.value){
		 $("#nameMsg").text("이름: 필수 입력입니다.")
		 $("#nameMsg").show();
		 $nameInput.focus();
		 jsValid = false;
	}else{
		$("#nameMsg").hide();
		jsValid = true;
	}
});

//아이디 중복체크 + 유효성
let $idInput = $("#id");
let $ckIdBtn = $("#ckIdBtn");
let ckIdValid = false;
let idReg = /^[a-zA-Z][0-9a-zA-Z]{6,18}$/;
$idInput.on("change", (e) =>{
	if(!e.target.value){
		$("#idMsg").text("아이디: 필수 입력입니다.")
		$("#idMsg").show();
		$idInput.focus();
		jsValid = false;
		return;
	}
	if(!idReg.test(e.target.value)){
		$("#idMsg").text("아이디: 영문을 포함한 영문 + 숫자 조합 6~18 자리를 사용하세요.")
		$("#idMsg").show();
		$idInput.focus();
		jsValid = false;
		return;
	}
	jsValid = true;
});
$ckIdBtn.on("click", () => {
	let id = $idInput.val();
	if (!id) {
		alert("아이디 입력");
		$idInput.focus();
		return;
	}
	let dataObj = { id };
	$.ajax({
		url: `${ctxPath}/member/ckId`,
		type: "POST",
		data: dataObj,
		success: function onData(hasId) {
			if (hasId) {
				alert("아이디 중복.");
				ckIdValid = false;
			} else {
				alert("아이디 사용가능.");
				ckIdValid = true;
			}
		},
		error: function onError(error) {
			console.error(error);
		},
	});
});

// 비밀번호 유효성 검사
let $pwInput = $("#pw");
$pwInput.on("change", (e)=>{
	
});

// 생년월일 포맷 유효성 검사
let $birth = $("#birth");
let $birthMsg = $("#birthMsg");
$birth.on("change", (e) => {
	// 생년월일 입력 검증
	if (e.target.value.length != 8 || isNaN(e.target.value)) {
		formValid = false;
		$birthMsg.text("생년월일: 8자리 숫자를 입력해주세요.");
		$birthMsg.show();
	} else {
		let year = $birth.val().substr(0, 4);
		let month = $birth.val().substr(4, 2);
		let day = $birth.val().substr(6, 2);
		let birthFormat = `${year}/${month}/${day}`;
		$birth.val(birthFormat);
		$birthMsg.hide();
		formValid = true;
	}
});

// form 서브밋 이벤트
let $joinfrm = $(document.joinfrm);
$joinfrm.on("submit", (e) => {
	e.preventDefault();
	if (!ckIdValid) {
		alert("아이디 중복확인 해주세요.");
		return;
	}
	if(!jsValid){
		alert("폼이 완성되지 않았습니다.");
		return;
	}

	$joinfrm.submit();
});