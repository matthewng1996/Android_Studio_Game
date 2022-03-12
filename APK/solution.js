function getDamage() {
	var dmg = Java.use("com.workshop.hackventure.PlayerBullet");

	dmg.getDamage.overload().implementation = function () {
		console.log("Hooked damaged function");
		var result = this.getDamage();
		console.log("result = " + result);
		result = 100000;
		return result;
	}
}

Java.perform(function() {
	getDamage();
});
