import leb128
 
def decrypt(_slot0, _slot1, _slot2):
    _slot0 ^= _slot1
    _slot0 = ((_slot0 >> 3) | (_slot0 << 61)) & 0xffffffffffffffff
    slot1_and_slot0 = _slot1 ^ _slot2
    _slot1 = slot1_and_slot0 - _slot0
    _slot1 &= 0xffffffffffffffff
    _slot1 = ((_slot1 >> 56) | (_slot1 << 8)) & 0xffffffffffffffff
    return _slot0, _slot1
 
def u64_to_uleb128(u):
    high = u >> 32
    low = u & 0xffffffff
    return leb128.u.encode(low).hex() + leb128.u.encode(high).hex()
 
slot2_array = [0xdeadbeef12345678, 0x28539dc5904d8141, 0xf2ac321ccf237a7b, 0xf03df21e866b1a36, 0x584cde754c325b4b, 0x97407269ac231f8b,
 0xd2960ba60ee82d09, 0xb34efc0e8d197592, 0x15011adba4d8613d, 0x1598470b72677cea, 0xb497efc6db87c606, 0xae0f3ba8a4eeb218,
 0xab6036ab64121254, 0x663ae5cc72c5eb7f, 0x71af0f7e9c371b0e, 0xeb97fc6b58f9eb33, 0x774108a83f7c75f6, 0x5a6542d5c9968681,
 0x5e6fb973117ccfb1, 0xea8134ba653ce534, 0xfc92946aa1cc9678, 0x38af8cc9553071e4, 0x99f7a1b258084992, 0x82e920e890bb99da,
 0xc67f72528ed05d6c, 0x4cab3a53d2598281, 0x517358620b3249f9, 0xcf3d41fd5e5e0786, 0x626be66ab995efe3, 0x24d85b01f54e2ab1,
 0xe9cd3a65e3f95992, 0x4bf5996751882d17]

slot2_array.reverse()
 
slot0 = 0xdd26c29515a28396
slot1 = 0xbd722d4baf99b9c7

for slot2 in slot2_array:
    slot0, slot1 = decrypt(slot0, slot1, slot2)
 
print(u64_to_uleb128(slot1) + u64_to_uleb128(slot0))
# b0e09cfb05e5e4fdaa07d4e2a8fa05f480fda206