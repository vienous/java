/** 
 *  ����ͼʵ�֣���ͼƬ(jpg��bmp��png��gif�ȵ�)��ʵ�ı����Ҫ�Ĵ�С 
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
 * ����ͼ�ࣨͨ�ã� ��java���ܽ�jpg��bmp��png��gifͼƬ�ļ������еȱȻ�ǵȱȵĴ�Сת���� ����ʹ�÷��� 
 * compressPic(��ͼƬ·��,����СͼƬ·��,��ͼƬ�ļ���,����СͼƬ����,����СͼƬ���,����СͼƬ�߶�,�Ƿ�ȱ�����(Ĭ��Ϊtrue)) 
 */  
 public class Compress {   
     private File file = null; // �ļ�����   
     private String inputDir; // ����ͼ·��  
     private String outputDir; // ���ͼ·��  
     private String inputFileName; // ����ͼ�ļ���  
     private String outputFileName; // ���ͼ�ļ���  
     private int outputWidth = 100; // Ĭ�����ͼƬ��  
     private int outputHeight = 100; // Ĭ�����ͼƬ��  
     private boolean proportion = true; // �Ƿ�ȱ����ű��(Ĭ��Ϊ�ȱ�����)  
     public Compress() { // ��ʼ������  
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
      * ���ͼƬ��С  
      * ������� String path ��ͼƬ·��  
      */   
     public long getPicSize(String path) {   
         file = new File(path);   
         return file.length();   
     }  
       
     // ͼƬ����   
     public String compressPic() {   
         try {   
             //���Դ�ļ�   
             file = new File(inputDir + inputFileName);   
             if (!file.exists()) {   
                 return "";   
             }   
             Image img = ImageIO.read(file);   
             // �ж�ͼƬ��ʽ�Ƿ���ȷ   
             if (img.getWidth(null) == -1) {  
                 System.out.println(" can't read,retry!" + "<BR>");   
                 return "no";   
             } else {   
                 int newWidth; int newHeight;   
                 // �ж��Ƿ��ǵȱ�����   
                 if (this.proportion == true) {   
                     // Ϊ�ȱ����ż��������ͼƬ��ȼ��߶�   
                     double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;   
                     double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;   
                     // �������ű��ʴ�Ľ������ſ���   
                     double rate = rate1 > rate2 ? rate1 : rate2;   
                     newWidth = (int) (((double) img.getWidth(null)) / rate);   
                     newHeight = (int) (((double) img.getHeight(null)) / rate);   
                 } else {   
                     newWidth = outputWidth; // �����ͼƬ���   
                     newHeight = outputHeight; // �����ͼƬ�߶�   
                 }   
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
                  
                /* 
                 * Image.SCALE_SMOOTH �������㷨 ��������ͼƬ��ƽ���ȵ� 
                 * ���ȼ����ٶȸ� ���ɵ�ͼƬ�����ȽϺ� ���ٶ��� 
                 */   
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null); 
                FileOutputStream out = new FileOutputStream(outputDir + outputFileName);  
                // JPEGImageEncoder������������ͼƬ���͵�ת��   
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
        // ����ͼ·��   
        this.inputDir = inputDir;   
        // ���ͼ·��   
        this.outputDir = outputDir;   
        // ����ͼ�ļ���   
        this.inputFileName = inputFileName;   
        // ���ͼ�ļ���  
        this.outputFileName = outputFileName;   
        return compressPic();   
    }   
    public String compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName, int width, int height, boolean gp) {   
        // ����ͼ·��   
        this.inputDir = inputDir;   
        // ���ͼ·��   
        this.outputDir = outputDir;   
        // ����ͼ�ļ���   
        this.inputFileName = inputFileName;   
        // ���ͼ�ļ���   
        this.outputFileName = outputFileName;   
        // ����ͼƬ����  
        setWidthAndHeight(width, height);   
        // �Ƿ��ǵȱ����� ���   
        this.proportion = gp;   
        return compressPic();   
    }  
    

      
    // main����   
    // compressPic(��ͼƬ·��,����СͼƬ·��,��ͼƬ�ļ���,����СͼƬ����,����СͼƬ���,����СͼƬ�߶�,�Ƿ�ȱ�����(Ĭ��Ϊtrue))  
    public static void main(String[] arg){   
    	String intputDir = "e:\\img_resource\\";
    	String outputDir = "e:\\img_compress\\";
    	File file = new File(intputDir);     
        File[] array = file.listFiles(); 
        System.out.println("��ͼƬ����:"+array.length);
        int cCount = 0;
        int uCount = 0; 
        for (int i = 0; i < array.length; i++) {   
        	String imgName = array[0].toString().split("\\\\")[2];       	
        	Compress mypic = new Compress();          	
        	long imgSzie = mypic.getPicSize(array[i].toString())/1024;
        	System.out.println("���"+(i+1)+":�����ͼƬ"+imgName+"��С��" + mypic.getPicSize(array[i].toString())/1024 + "KB");
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
                System.out.println("�����ͼƬ��С��" + mypic.getPicSize(outputDir+imgName)/1024 + "KB"); 
        	}else{
        		//С��100K,������ȥ
        		FileInputStream input =null;
        		FileOutputStream output =null;
        		try{
        			input=new FileInputStream(array[i]);//���滻Ϊ�κ�·���κ��ļ���
        			output=new FileOutputStream(outputDir+imgName);//���滻Ϊ�κ�·���κ��ļ���
        			int in=input.read();
        			while(in!=-1){
	        			output.write(in);
	        			in=input.read();
        			}
        			uCount++;
        			System.out.println("ͼƬ"+imgName+"С��100K,δѹ��");
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
        System.out.println("ѹ��ͼƬ����Ϊ:"+cCount);
        System.out.println("δѹ��ͼƬ����Ϊ:"+uCount);
        
    }   
 }  