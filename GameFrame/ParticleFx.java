import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ConfigurableEmitter.LinearInterpolator;
import org.newdawn.slick.particles.ConfigurableEmitter.SimpleValue;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;

public class ParticleFx extends SimpleGameObject {

	private static ConfigurableEmitter smallExplosionEmitter; // initial explosion - will be duplicated and placed as needed
	private static ConfigurableEmitter rain; // initial explosion - will be duplicated and placed as needed
	private static ParticleSystem effectSystem; // stores all particle effects
	public boolean first = true;
	public ParticleFx(int x, int y, Vector2f pos, ObjectPool objPool) {
		super(x, y, pos, objPool);
		init();
	}

	public ParticleFx(){
		super();
	}

	private void init(){

		/** Initialize particle explosion - Use preconfigured explosion.xml as the system */
		this.checkForCollision = false;
		this.checkForGravity = false;      
		try
		{   
			// EXPLOSION SET_UP
			effectSystem = ParticleIO.loadConfiguredSystem("particle/explosion.xml");
			effectSystem.getEmitter(0).setEnabled(false); // disable the initial emitter
			effectSystem.setRemoveCompletedEmitters(true); // remove emitters once they finish
			// EXPLOSION SET_UP
			/** Create a new emitter based on the explosionSystem - set disabled */
			smallExplosionEmitter = (ConfigurableEmitter)effectSystem.getEmitter(0);
			smallExplosionEmitter.setEnabled(false);
			
			try {
				//load the test particle and 
				Image image = new Image("particle/test_particle.png", false);
				effectSystem = new ParticleSystem(image,1500);
				String xmlFile = "particle/test_emitter.xml";
				rain = ParticleIO.loadEmitter(xmlFile);
				rain.setPosition(400, 100);
				effectSystem.addEmitter(rain);
			} catch (Exception e) {
				System.exit(0);
			}
			effectSystem.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
			
			
		}
		catch(Exception e)
		{
			Sys.alert("Error", "Error adding explosion\nCheck for explosion.xml");
			System.exit(0);
		}
	}


	/** Add an explosion at specified x and y coordinates */
	public static void addExplosion(float x, float y) 
	{
		ConfigurableEmitter e = smallExplosionEmitter.duplicate(); // copy initial emitter
		e.setEnabled(true); // enable
		e.setPosition(x, y);
		effectSystem.addEmitter(e); // add to particle system for rendering and updating
	}
	
	public static void addRain(float x, float y) 
	{
		ConfigurableEmitter e = rain.duplicate(); // copy initial emitter
		e.setEnabled(true); // enable
		e.setPosition(x, y);
		effectSystem.addEmitter(e); // add to particle system for rendering and updating
	}


	@Override
	void init(GameContainer gc) {
		// TODO Auto-generated method stub

	}


	@Override
	void update(GameContainer gc, int delta,StateBasedGame sbg) {
		

		effectSystem.update(delta);
		effectSystem.setRemoveCompletedEmitters(true);
		
		if(Math.random()<-1){
			//ObjectPool op = new ObjectPool();
			SimpleGameObject p = this.objPool.getCollisionPool().get(0);
			double rng = Math.random();
			System.out.println(effectSystem.getEmitterCount());
			if(rng<0.3)
			{
				this.addRain(p.gamePosition.x-400, p.gamePosition.y-400);
				
			}
			else if(rng>0.3&&rng<0.6)
			{
				this.addRain(p.gamePosition.x, p.gamePosition.y-400);
				
			}
			else{
				{
					this.addRain(p.gamePosition.x+700, p.gamePosition.y-400);
					
				}
			}
			
		}
		

	}


	@Override
	void render(GameContainer gc, Graphics g) {
		// In render method
		effectSystem.setBlendingMode(effectSystem.BLEND_ADDITIVE);
		effectSystem.render();
	}


	@Override
	void reset() {
		// TODO Auto-generated method stub

	}
	
	public void addWeather(){
		
	}

	@Override
	public void objectCollide(SimpleGameObject sGO) {
		// TODO Auto-generated method stub

	}

}
