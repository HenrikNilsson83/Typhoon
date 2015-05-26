import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;



public class Audio_Player {
	public static Hashtable<String,Music> musicMap;
	private static Hashtable<String, Sound> soundMap;
	private static boolean reset = true;
	
	public Audio_Player(Boolean reset){
		if(reset){
			this.musicMap = new Hashtable<String,Music>();
			this.soundMap = new Hashtable<String,Sound>();
		}
	}
	
	public void setVolume(String id,float volume){
		this.musicMap.get(id).setVolume(volume);
	}
	
	public Audio_Player(){
		if(reset){
			this.musicMap = new Hashtable<String,Music>();
			this.soundMap = new Hashtable<String,Sound>();
			reset = false;
		}
	}
	
	public void addMusic(String id,String path){
		MusicResource tempR = new MusicResource(id,path);
		musicMap.put(id,tempR.getMusic());
	}
	public void playMusic(String id,float volume){
		musicMap.get(id).play(1, volume);
	}
	
	public void loopMusic(String id,float volume){
		if(!musicMap.get(id).playing())
			musicMap.get(id).loop(1, volume);
	}
	
	public void fadeMusic(String id,int duration,float endVolume,boolean stopAfterFade){
		musicMap.get(id).fade(duration, endVolume, stopAfterFade);
	}
	
	public boolean isPlaying(String id){
		
		if(musicMap.containsKey(id))
		{
			return musicMap.get(id).playing();
		}
		if(soundMap.containsKey(id)){
			return soundMap.get(id).playing();
		}
		else 
			return false;
	}
	
	//SOUND
	
	public void addSound(String id, String path) {
		SoundResource tempR = new SoundResource(id,path);
		soundMap.put(id, tempR.getSound());
		
	}
	
	public void playSound(String id,float pitch,float volume){
		soundMap.get(id).play(pitch, volume);
		
	}
}
