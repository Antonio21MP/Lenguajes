from PIL import Image
import base64
#enc = open('./decode.txt')
#nc.decode('base64','strict')
def black_and_white(name, data):
    dec = data.decode('base64', 'strict')
    with open(name, 'wb') as f:
        f.write(dec)
    
    img = Image.open(name)
    pixels = img.load()
    width, heigth = img.size

    print "before: "
    print pixels[0, 0] 
    for x in range(width):
        for y in range(heigth):
            color_r = pixels[x, y][0]
            color_g = pixels[x, y][1]
            color_b = pixels[x, y][2]
            average =((color_r+color_g+color_b)/3)
            tmp = (average, average, average)
            img.putpixel((x, y), tmp)
    print "after: "
    print pixels[0, 0]
    img.save(name)
    with open(name, "rb") as f:
        img_open = f.read()
        enc = img_open.encode('base64', 'strict')
    #enc.replace('\n','')
    enc = base64.b64encode(bytes(enc),"utf-8")
    json_result = '{ \"nombre\":\"'+name+'\", \"data\":\"'+enc+'\"}'
    
    print json_result
    return json_result
