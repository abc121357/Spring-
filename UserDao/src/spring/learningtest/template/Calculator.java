package spring.learningtest.template;

import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class Calculator {


	public Integer calcSum(String filepath) throws IOException {
		LineCallback<Integer> sumCallback =
				new LineCallback<Integer>() {
					public Integer doSomethingWithLine(String line,Integer value) {
						return value + Integer.valueOf(line);
					}
				};
				return lineReadTemplate(filepath, sumCallback, 0);
	}
	//곱을 계산하는 콜백을 가진 메소드
	public Integer calcMultiply(String filepath) throws IOException{
		LineCallback<Integer> multiplyCallback =
				new LineCallback<Integer>() {
					public Integer doSomethingWithLine(String line,Integer value) {
						return value * Integer.valueOf(line);
					}
				};

				return lineReadTemplate(filepath,multiplyCallback, 1);
	}

	public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			int ret = callback.doSomethingWithReader(br);
			return ret;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public <T> T lineReadTemplate(String filepath, LineCallback<T> callback,T initVal)throws IOException{
		BufferedReader br =null;
		try{
			br = new BufferedReader(new FileReader(filepath));
			T res = initVal;
			String line = null;
			while((line = br.readLine())!=null){
				res = callback.doSomethingWithLine(line,res);
			}

			return res;
		}catch (IOException e){
			System.out.println(e.getMessage());
			throw e;
		}finally {
			if (br!=null){
				try{
					br.close();
				}catch(IOException e){
					System.out.println(e.getMessage());
				}
			}
		}

	}

	public String concatenate(String filepath)throws IOException{
		LineCallback<String> concatenateCallback=
				new LineCallback<String>(){
				public String doSomethingWithLine(String line,String value){
					return value + line;
				}};

				return lineReadTemplate(filepath,concatenateCallback, "");

	}


}
