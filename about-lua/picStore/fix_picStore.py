import struct
fp = open(r"C:\\Users\\23957\\Desktop\\pic\\log.log","r")
fps = open(r"C:\\Users\\23957\\Desktop\\pic\\picStore.bin","rb")
fix_bin = open(r"C:\\Users\\23957\\Desktop\\pic\\fix_picStore.bin","wb")
# data为要修改的地方，data_bin为原始文件
data = fp.readlines()
data_bin = fps.read()
fix_data = b""

ans = 0
for i in range(len(data)):
    op_str = data[i]
    # loa为addr，step为字节数
    loa = int(op_str[2:6],16) + 1
    step = int(op_str[14:15],16)  
    # 将要修改的addr之前的内容加入到fix_data里，这部分并没有被修改
    for j in range(ans, loa):
        fix_data += struct.pack("B",data_bin[j])

    for j in range(loa, loa + step):
        if data_bin[j] != 0 and data_bin[j] != 0xff:
            # 取反
            t = data_bin[j] ^ 0xff
            fix_data += struct.pack("B", t)
        else:
            # 若为0或0xff就不去反，为什么？
            fix_data += struct.pack("B", data_bin[j])
    ans = loa + step
fix_bin.write(fix_data)
fix_bin.close()