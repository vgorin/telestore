const Migrations = artifacts.require("Migrations");

module.exports = function(deployer, network) {
  if(network === "test") {
    console.log("[initial migration] test network - skipping the migration script");
    return;
  }
  if(network === "coverage") {
    console.log("[initial migration] coverage network - skipping the migration script");
    return;
  }

  deployer.deploy(Migrations);
};
