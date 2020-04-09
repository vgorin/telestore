const SimpleToken = artifacts.require("SimpleToken");

module.exports = async function(deployer, network, accounts) {
	if(network === "test") {
		console.log("[deploy SimpleToken] test network - skipping the migration script");
		return;
	}
	if(network === "coverage") {
		console.log("[deploy SimpleToken] coverage network - skipping the migration script");
		return;
	}

	await deployer.deploy(SimpleToken);
};
