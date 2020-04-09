pragma solidity 0.5.8;

contract SimpleToken {
  string public constant name = "SimpleToken";
  string public constant symbol = "STK";
  uint8 public constant decimals = 6;

  uint256 public constant totalSupply = 1 ether; // 1 trillion
  mapping(address => uint256) public balanceOf;

  event Transfer(address indexed _from, address indexed _to, uint256 _value);

  constructor() public {
    balanceOf[msg.sender] = totalSupply;
  }

  function transfer(address _to, uint256 _value) public returns (bool) {
    require(balanceOf[msg.sender] >= _value);

    balanceOf[msg.sender] -= _value;
    balanceOf[_to] += _value;

    emit Transfer(msg.sender, _to, _value);
    return true;
  }
}
