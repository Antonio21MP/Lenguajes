from PIL import Image
import base64
#enc = open('./decode.txt')
#nc.decode('base64','strict')

def do_resize(img, box, fit, name):
    factor = 1
    while img.size[0]/factor > 2*box[0] and img.size[1]*2/factor > 2*box[1]:
        factor *=2
    if factor > 1:
        img.thumbnail((img.size[0]/factor, img.size[1]/factor), Image.NEAREST)
    
    if fit:
        x1 = y1 = 0
        x2, y2 = img.size
        wRatio = 1.0 * x2/box[0]
        hRatio = 1.0 * y2/box[1]
        if hRatio > wRatio:
            y1 = int(y2/2-box[1]*wRatio/2)
            y2 = int(y2/2+box[1]*wRatio/2)
        else:
            x1 = int(x2/2-box[0]*hRatio/2)
            x2 = int(x2/2+box[0]*hRatio/2)
        img = img.crop((x1,y1,x2,y2))

    img.thumbnail(box, Image.ANTIALIAS)

    img.save(name)
    """
    with open(name, "rb") as f:
        img_open = f.read()
        enc = img_open.encode('base64', 'strict')
    #enc.replace('\n','')
    enc = base64.b64encode(bytes(enc),"utf-8")
    json_result = '{\"nombre\":\"'+name+'\",\"data\":\"'+enc+'\"}'
    
    print json_result
    """
"""
img = Image.open('./output.bmp')
pixels = img.load()
width, heigth = img.size
print width
print heigth
new_width = 100
new_heigth = 100
#new_img = Image.new("RGB",(new_width, new_heigth))
#print len(bin(pixels[0,0]))

get_reduce(img,(new_width, new_heigth), True)
red = Image.open('./resize.bmp')
print red.size
"""