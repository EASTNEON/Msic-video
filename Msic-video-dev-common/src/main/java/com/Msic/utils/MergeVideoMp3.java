package com.Msic.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MergeVideoMp3 {
	
	private String ffmpegEXE;

	public MergeVideoMp3(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}

	public void convertor(String videoInputPath, 
			String mp3InputPath, double seconds,
			String videoOutputPath) throws  Exception{
	//	ffmpeg.exe -i bgm.mp3 -i cyj.mp4 -t 4 -y new.mp4
		
		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);
		
		
		command.add("-i");
		command.add(mp3InputPath);
		
		command.add("-i");
		command.add(videoInputPath);
		
	
		
		command.add("-t");
		command.add(String.valueOf(seconds));
		
		command.add("-y");
		command.add(videoOutputPath);
		
		/*
		 * for (String c : command) { System.out.print(c); }
		 */
		
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
		MergeVideoMp3 ffmpeg = new MergeVideoMp3("D:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			ffmpeg.convertor("D:\\Program Files\\cyj.mp4", "D:\\Program Files\\bgm.mp3", 4.0,  "D:\\Program Files\\new.mp4");
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

}
