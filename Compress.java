/** 
 *  缩略图实现，将图片(jpg、bmp、png、gif等等)真实的变成想要的大小 
 */  
package compress;  
  
import java.awt.Image;  
import java.awt.image.BufferedImage;  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;  
import java.io.IOException;  
import javax.imageio.ImageIO;  
import com.sun.image.codec.jpeg.JPEGCodec;  
import com.sun.image.codec.jpeg.JPEGImageEncoder;  
  
/******************************************************************************* 
 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法 
 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true)) 
 */  
 public class Compress {   
     private File file = null; // 文件对象   
     private String inputDir; // 输入图路径  
     private String outputDir; // 输出图路径  
     private String inputFileName; // 输入图文件名  
     private String outputFileName; // 输出图文件名  
     private int outputWidth = 100; // 默认输出图片宽  
     private int outputHeight = 100; // 默认输出图片高  
     private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)  
     public Compress() { // 初始化变量  
         inputDir = "";   
         outputDir = "";   
         inputFileName = "";   
         outputFileName = "";   
         outputWidth = 100;   
         outputHeight = 100;   
     }   
     public void setInputDir(String inputDir) {   
         this.inputDir = inputDir;   
     }   
     public void setOutputDir(String outputDir) {   
         this.outputDir = outputDir;   
     }   
     public void setInputFileName(String inputFileName) {   
         this.inputFileName = inputFileName;  
     }   
     public void setOutputFileName(String outputFileName) {   
         this.outputFileName = outputFileName;   
     }   
     public void setOutputWidth(int outputWidth) {  
         this.outputWidth = outputWidth;   
     }   
     public void setOutputHeight(int outputHeight) {   
         this.outputHeight = outputHeight;   
     }   
     public void setWidthAndHeight(int width, int height) {   
         this.outputWidth = width;  
         this.outputHeight = height;   
     }   
       
     /*  
      * 获得图片大小  
      * 传入参数 String path ：图片路径  
      */   
     public long getPicSize(String path) {   
         file = new File(path);   
         return file.length();   
     }  
       
     // 图片处理   
     public String compressPic() {   
         try {   
             //获得源文件   
             file = new File(inputDir + inputFileName);   
             if (!file.exists()) {   
                 return "";   
             }   
             Image img = ImageIO.read(file);   
             // 判断图片格式是否正确   
             if (img.getWidth(null) == -1) {  
                 System.out.println(" can't read,retry!" + "<BR>");   
                 return "no";   
             } else {   
                 int newWidth; int newHeight;   
                 // 判断是否是等比缩放   
                 if (this.proportion == true) {   
                     // 为等比缩放计算输出的图片宽度及高度   
                     double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;   
                     double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;   
                     // 根据缩放比率大的进行缩放控制   
                     double rate = rate1 > rate2 ? rate1 : rate2;   
                     newWidth = (int) (((double) img.getWidth(null)) / rate);   
                     newHeight = (int) (((double) img.getHeight(null)) / rate);   
                 } else {   
                     newWidth = outputWidth; // 输出的图片宽度   
                     newHeight = outputHeight; // 输出的图片高度   
                 }   
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
                  
                /* 
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
                 * 优先级比速度高 生成的图片质量比较好 但速度慢 
                 */   
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null); 
                FileOutputStream out = new FileOutputStream(outputDir + outputFileName);  
                // JPEGImageEncoder可适用于其他图片类型的转换   
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
                encoder.encode(tag);   
                out.close();   
             }   
         } catch (IOException ex) {   
             ex.printStackTrace();   
         }   
         return "ok";   
    }   
    public String compressPic (String inputDir, String outputDir, String inputFileName, String outputFileName) {   
        // 输入图路径   
        this.inputDir = inputDir;   
        // 输出图路径   
        this.outputDir = outputDir;   
        // 输入图文件名   
        this.inputFileName = inputFileName;   
        // 输出图文件名  
        this.outputFileName = outputFileName;   
        return compressPic();   
    }   
    public String compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName, int width, int height, boolean gp) {   
        // 输入图路径   
        this.inputDir = inputDir;   
        // 输出图路径   
        this.outputDir = outputDir;   
        // 输入图文件名   
        this.inputFileName = inputFileName;   
        // 输出图文件名   
        this.outputFileName = outputFileName;   
        // 设置图片长宽  
        setWidthAndHeight(width, height);   
        // 是否是等比缩放 标记   
        this.proportion = gp;   
        return compressPic();   
    }  
    

      
    // main测试   
    // compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))  
    public static void main(String[] arg){   
    	String intputDir = "e:\\img_resource\\";
    	String outputDir = "e:\\img_compress\\";
    	File file = new File(intputDir);     
        File[] array = file.listFiles(); 
        System.out.println("总图片个数:"+array.length);
        int cCount = 0;
        int uCount = 0; 
        for (int i = 0; i < array.length; i++) {   
        	String imgName = array[0].toString().split("\\\\")[2];       	
        	Compress mypic = new Compress();          	
        	long imgSzie = mypic.getPicSize(array[i].toString())/1024;
        	System.out.println("序号"+(i+1)+":输入的图片"+imgName+"大小：" + mypic.getPicSize(array[i].toString())/1024 + "KB");
        	if(imgSzie>100){
        		int width = 2000;
        		int heigth = 1600;
        		mypic.compressPic(intputDir, outputDir, imgName, imgName,width,heigth,true);
        		long imgSizetemp = mypic.getPicSize(outputDir+imgName)/1024;
        		while(imgSizetemp>100){
        			width = width -100;
        			heigth = heigth -100;
        			mypic.compressPic(intputDir, outputDir, imgName, imgName,width,heigth,true);
        			imgSizetemp = mypic.getPicSize(outputDir+imgName)/1024;
        			System.out.println(imgSizetemp);
        		}                 
        		cCount++;
                System.out.println("输出的图片大小：" + mypic.getPicSize(outputDir+imgName)/1024 + "KB"); 
        	}else{
        		//小于100K,拷贝过去
        		FileInputStream input =null;
        		FileOutputStream output =null;
        		try{
        			input=new FileInputStream(array[i]);//可替换为任何路径何和文件名
        			output=new FileOutputStream(outputDir+imgName);//可替换为任何路径何和文件名
        			int in=input.read();
        			while(in!=-1){
	        			output.write(in);
	        			in=input.read();
        			}
        			uCount++;
        			System.out.println("图片"+imgName+"小于100K,未压缩");
        		}catch (IOException e){
        			System.out.println(e.toString());
        		}finally{
        			try {
						input.close();
						output.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			
        		}
        	}
              
        }  
        System.out.println("压缩图片数量为:"+cCount);
        System.out.println("未压缩图片数量为:"+uCount);
        
    }   
 }  