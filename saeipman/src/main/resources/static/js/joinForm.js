/**
 * joinForm.js
 */

// 생년월일 포맷
let $birth = $("#floatingBirth");
let $birthMsg = $("#birthMsg")
$birth.on("change", (e) => { // 생년월일 입력 검증
	if (e.target.value.length != 8 || isNaN(e.target.value)) {
		$birthMsg.text("생년월일: 8자리 숫자를 입력해주세요.");
		$birthMsg.show();
	} else {
		let year = $birth.val().substr(0, 4);
		let month = $birth.val().substr(4, 2);
		let day = $birth.val().substr(6, 2);
		let birthFormat = `${year}/${month}/${day}`;
		$birth.val(birthFormat);
		$birthMsg.hide();
	}
});

