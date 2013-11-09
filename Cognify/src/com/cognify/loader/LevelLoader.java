package com.cognify.loader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.cognify.geometry.Shape;

import android.util.Xml;

public class LevelLoader {
	File xmlFile;
	
	public List parse(InputStream in) throws XmlPullParserException, IOException{
		try{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readFeed(parser);
		} finally {
			in.close();
		}
		
	}
	
	private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
		List shapes = new ArrayList();
		
		while(parser.next() != XmlPullParser.END_TAG)
		{
			if(parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			String name = parser.getName();
			
			if(name.equals("shape"))
			{
				shapes.add(readEntry(parser))
			}
			else
				skip(parser);
		}
		
		return shapes;
	}
	
	//entries in theirs = shapes in ours
	
	
	private Shape readEntry(XmlPullParser parser) throws XmlPullParserException, IOException{
		String type = null;
		String x = null;
		String y = null;
		
		while(parser.next() != XmlPullParser.END_TAG)
		{
			if(parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			String name = parser.getName();
			if(name.equals("type"))
				type = readType(parser);
			else if (name.equals("x"))
				x = readX(parser);
			else if(name.equals("y"))
				y = readY(parser);
			
		}
		
		
		
		
		return null;
		
	}
	

}


