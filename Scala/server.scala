                        import java.io.IOException
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Base64
import java.lang.Object
import javax.imageio.ImageIO
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.awt.image.Raster
import java.awt.Color
import java.nio.charset.Charset
import scala.util.parsing.json.JSON
import util.control.Breaks._
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.File

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
object Server {
  def main(args: Array[String]): Unit = {
    
    var server = HttpServer.create(new InetSocketAddress(8080), 0)
    server.createContext("/ejercicio1", new ejercicio1())
    server.createContext("/ejercicio2", new ejercicio2())
    server.createContext("/ejercicio3", new ejercicio3())
    server.createContext("/ejercicio4", new ejercicio4())
    server.setExecutor(null)
    server.start()
  }
}

class ejercicio1() extends HttpHandler{
    override def handle(t: HttpExchange){
        if (t.getRequestMethod() == "POST") {
            println("ejericicio1")
            //recibir
            val os: OutputStream = t.getResponseBody()
            var input = t.getRequestBody()
            var response: Array[Byte] = Stream.continually(input.read).takeWhile(_ != -1).map(_.toByte).toArray
            val json_string = new String(response, Charset.forName("UTF-8"))
            //sacando valores del json
            val parts = json_string.split("\"")
            val origin = parts(3)
            val destiny = parts(7)
            //enviando datos
            
            val get_directions = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destiny + "&key=AIzaSyDxjE_B2p2t199wTZPwBErRiOmaqL1hkhM"
            val url = new URL(get_directions.replace(' ', '+'))
            //println(url)
            val br = new BufferedReader(new InputStreamReader(url.openStream()))
            //println(br)
            var maps: String = ""
            var temp: String = ""
            while(br.ready()){
                temp = br.readLine()
                //println(temp)
                maps = maps + temp
            }
            //println(maps)
            val legs = maps.split("end_location")
            var legs2 = new Array[String](legs.length-1)
            var x = 0
            while(x < (legs.length -1)){
                legs2(x) = legs(x+1)
                x += 1 
            }
            var count = 0
            var result : String = "{\"resultados\":["
            var j = 2
            println(legs2.length)
            for (i <- legs2){
                //println("INICIO")
                val tmp = i
                val tmp2 = tmp.replace(" ", "")
                
                while(j < tmp2.length){
                    result = result + tmp2(j)
                    //println("t: "+tmp2(j))
                    if(tmp2(j).equals('}')){
                        println("LO ROMPIO")
                        if(count < (legs2.length-1)){
                            result = result + ','
                        }
                        j = tmp2.length
                        count += 1
                    }
                    j += 1
                }
                j = 2
                //println("FINAL")
            }
            println(result.length)
            var json_directions = result + "]}"
            println(json_directions)
            response = json_directions.getBytes(Charset.forName("UTF-8"))
            t.getResponseHeaders().add("content-type", "json")
            t.sendResponseHeaders(200, response.size.toLong)
            os.write(response)
            os.close()


        }
    }
}

class ejercicio2() extends HttpHandler{
    override def handle(t: HttpExchange){
        if (t.getRequestMethod() == "POST") {
            println("ejercicio2")
            val os: OutputStream = t.getResponseBody()
            var input = t.getRequestBody()
            var response: Array[Byte] = Stream.continually(input.read).takeWhile(_ != -1).map(_.toByte).toArray
            val json_string = new String(response, Charset.forName("UTF-8"))
            //sacando valores del json
            val parts = json_string.split("\"")
            val origin = parts(3)
            //enviando datos
            
            val get_directions = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + origin + "&key=AIzaSyDxjE_B2p2t199wTZPwBErRiOmaqL1hkhM"
            val url = new URL(get_directions.replace(' ', '+'))
            //println(url)
            val br = new BufferedReader(new InputStreamReader(url.openStream()))
            //println(br)
            var maps: String = ""
            var temp: String = ""
            while(br.ready()){
                temp = br.readLine()
                //println(temp)
                maps = maps + temp
            }
            val legs = maps.split("start_location")
            //println(legs(1))
            val tmp = legs(1).replace(" ","")
            var i = 9
            var values : String = ""
            //println(tmp)
            while(i < tmp.length){
                values = values + tmp(i)
                if(tmp(i).equals('}')){
                    println("LO ROMPIO")
                    i = tmp.length
                }
                i+= 1
            }
            val get_values = values.split(",")
            //println(get_values(1))
            var lat = get_values(0)
            val lng_c = get_values(1).replace("\"lng\":","")
            val lng = lng_c.replace("}","")
            val origin2 = lat +","+lng
            println(origin2)
            val get_places = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+origin2+"&radius=300&types=restaurant&key=AIzaSyDxjE_B2p2t199wTZPwBErRiOmaqL1hkhM"
            val url_p = new URL(get_places.replace(' ', '+'))
            //println(url)
            val brf = new BufferedReader(new InputStreamReader(url_p.openStream()))
            //println(br)
            var maps2: String = ""
            var temp2: String = ""
            while(brf.ready()){
                temp2 = brf.readLine()
                //println(temp)
                maps2 = maps2 + temp2
            }
            //println(maps2)
            val location_map = maps2.split("location") 
            val name_map = maps2.split("name")
            println(location_map(1))
            
            println("-----DIVISION-----")
            println(name_map(1))
            
            var count = 0
            var result1 : String = ""
            var result2 : String = ""
            var x = 0
            var location_tmp = new Array[String](location_map.length-1)
            while(x < (location_map.length -1)){
                location_tmp(x) = location_map(x+1)
                x+= 1
            }
            for (i <- location_tmp ){
                //println("INICIO")
                val tmp = i
                val tmp2 = tmp.replace(" ", "")
                var j = 3
                while(j < tmp2.length){
                    result1 = result1 + tmp2(j)
                    //println("t: "+tmp2(j))
                    if(tmp2(j).equals('}')){
                        println("LO ROMPIO")
                        if(count < (location_tmp.length-1)){
                            result1 = result1 + ','
                        }
                        j = tmp2.length
                        count += 1
                    }
                    j += 1
                }
                j = 3
                //println("FINAL")
            }
            //println("location: "+result1)
            
            var name_tmp = new Array[String](name_map.length-1)
            var y = 0
            while(y < (name_map.length -1)){
                name_tmp(y) = name_map(y+1)
                y+= 1
            }
            for(i <- name_tmp){
                val tmp = i
                var j = 4
                while(j < tmp.length){
                    result2 = result2 + tmp(j)
                    //println("t: "+tmp2(j))
                    if(tmp(j).equals(',')){
                        println("LO ROMPIO")
                        if(count < (name_tmp.length-1)){
                            result2 = result2 + ','
                        }
                        j = tmp.length
                        count += 1
                    }
                    j += 1
                }
                j = 4
            }
            //println(result2)
            val names = result2.split(",")
            val locations = result1.split(",")

            println("names: ")
            for(i <- names){
                println(i)
            }
            println("locations: ")
            for(i <- locations){
                println(i)
            }
            var a = 0
            var b = 0
            var json_result : String = "{\"restaurantes\":["
            while(a < names.length){
                json_result = json_result + "{\"nombre\":" + names(a) + ","  
                json_result = json_result + locations(b) + "," + locations(b+1)
                if(a == (names.length-1)){
                    a = names.length
                }else{
                json_result = json_result + ","
                b+=2
                a+=1
                }
            }
            var json_places = json_result + "]}"
            println(json_places)
            response = json_places.getBytes(Charset.forName("UTF-8"))
            t.getResponseHeaders().add("content-type", "json")
            t.sendResponseHeaders(200, response.size.toLong)
            os.write(response)
            os.close()
        }
    }
}

class ejercicio3() extends HttpHandler{
    override def handle(t: HttpExchange){
        if (t.getRequestMethod() == "POST") {
            println("ejercicio3")
            val os: OutputStream = t.getResponseBody()
            var input = t.getRequestBody()
            var response: Array[Byte] = Stream.continually(input.read).takeWhile(_ != -1).map(_.toByte).toArray
            val json_string = new String(response, Charset.forName("UTF-8"))
            //sacando valores del json
            val parts = json_string.split("\"")
            
            var name = parts(3)
            var data = parts(7)
            println(name)
            println(data)
            val decodedFile = new File(name)
            val base64 = Base64.getDecoder().decode(data)
            var in: ByteArrayInputStream = new ByteArrayInputStream(base64)
            var img: BufferedImage = ImageIO.read(in)
            var width = img.getWidth()
            var height = img.getHeight()
            println("w: "+width)
            println("h: "+height)
            var x = 0
            var y = 0
            while(y < height){
                while(x < width){
                    val pixel = img.getRGB(x,y)
                    val r = (pixel & 0xff0000) / 65536
                    val g = (pixel & 0xff00) / 256
                    val b = (pixel & 0xff)

                    val avg = (r+b+g)/3
                    println(avg)
                    img.setRGB(x,y,new Color(avg, avg, avg).getRGB)
                    x+=1
                }
                println("salio")
                y+=1
                x=0
            }
            var out: ByteArrayOutputStream = new ByteArrayOutputStream()
            ImageIO.write(img, "bmp", out)
            var new_img = out.toByteArray()
            var bw_img = Base64.getEncoder().encodeToString(new_img)
            var json_grayscale = "{\"nombre\":\""+name+"\",\"data\":\""+bw_img+"\"}"
            println("LLEGO")
            response = json_grayscale.getBytes(Charset.forName("UTF-8"))
            t.getResponseHeaders().add("content-type", "json")
            t.sendResponseHeaders(200, response.size.toLong)
            os.write(response)
            os.close()
        }
    }
}

class ejercicio4() extends HttpHandler{
    override def handle(t: HttpExchange){
        if (t.getRequestMethod() == "POST") {
            println("ejercicio4")
            
            val os: OutputStream = t.getResponseBody()
            var input = t.getRequestBody()
            var response: Array[Byte] = Stream.continually(input.read).takeWhile(_ != -1).map(_.toByte).toArray
            val json_string = new String(response, Charset.forName("UTF-8"))
            val parts = json_string.split("\"")
            
            val name = parts(3)
            val data = parts(7)
            val w = parts(12).split(":")
            val h = parts(14).split(":")
            val w2 = w(1).split(",")
            val h2 = h(1).split("}")
            println(w2(0))
            println(h2(0))
            var img_width = w2(0).toInt
            var img_height = h2(0).toInt
            println(img_width)
            println(img_height)
            
            var img= Base64.getDecoder().decode(data)
            var bais: ByteArrayInputStream = new ByteArrayInputStream(img)
            var editable_img: BufferedImage = ImageIO.read(bais)
            var smaller_img: BufferedImage = new BufferedImage(img_width, img_height, 1)
            
            var height = editable_img.getHeight()
            var width = editable_img.getWidth()
            
            var divX = width.toFloat/img_width.toFloat
            var divY = height.toFloat/img_height.toFloat

            println(divX)
            println(divY)
            
            var resizedWidth = (width/divX).toInt
            var resizedHeight = (height/divY).toInt
            println("" + width + " / " + img_width + " = " + divX)
            println("" + height + " / " + img_height + " = " + divY)

            for(x <- 0 to resizedWidth - 1){
                for(y <- 0 to resizedHeight - 1){
                    var pixel = editable_img.getRGB((x * divX).toInt, (y * divY).toInt)
                    smaller_img.setRGB(x, y, pixel)
                }
            }
            var baos: ByteArrayOutputStream = new ByteArrayOutputStream()
            ImageIO.write(smaller_img, "bmp", baos)
            var new_img = baos.toByteArray()
            val small_img = Base64.getEncoder().encodeToString(new_img)

            var json= ""
            println(name)
            var name2 = name.split("\\.")
            json = "{\"nombre\":\"" + name2(0) + "(reducida)." + name2(1) + "\", \"data\": \"" + small_img + "\"}"
            println(json)
            response = json.getBytes(Charset.forName("UTF-8"))
            
            t.getResponseHeaders().add("content-type", "json")
            t.sendResponseHeaders(200, response.size.toLong)
            os.write(response)
            os.close()
        }
    }
}