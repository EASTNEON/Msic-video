package com.Msic.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
 * 
 * @Description:获取视频信息
 * */
public class FetchVideoCover {
	// 视频路径
	private String ffmpegEXE;

	public FetchVideoCover(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}

	public void getCover(String videoInputPath,
			String CoverOutputPath) throws  Exception{
	//	ffmpeg.exe -ss 00:00:01 -y -i cyj.mp4 -vframes 1 new.png
		
		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);
		
		//指定截取第一秒
		command.add("-ss");
		command.add("00:00:01");
		
		command.add("-y");
		
		command.add("-i");
		command.add(videoInputPath);
		
		command.add("-vframes");
		command.add("1");
		command.add(CoverOutputPath);
		
		
		
		for (String c : command) { System.out.print(c); }
		 
		
		ProcessBuilder buidlder = new ProcessBuilder(command);
		
		Process process= buidlder.start();
		
		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		
		BufferedReader br = new BufferedReader(inputStreamReader);
		
		String line  = "";
		while ((line = br.readLine()) != null) {
			
			
		}
		
		if(br != null) {
			br.close();
		}
		
		if(inputStreamReader != null) {
			inputStreamReader.close();
		}
		if(errorStream != null) {
			errorStream.close();
		}
	}
	
	public static void main(String[] args) {
		FetchVideoCover videoInfo = new FetchVideoCover("D:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			videoInfo.getCover("D:\\Program Files\\cyj.mp4", "D:\\Program Files\\cyj.jpg");
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

}
