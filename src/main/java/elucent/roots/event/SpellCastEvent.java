package elucent.roots.event;

import elucent.roots.component.ComponentBase;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SpellCastEvent extends Event {
	double potency = 0;
	double efficiency = 0;
	double size = 0;
	ComponentBase component = null;
	public SpellCastEvent(ComponentBase component, double potency, double efficiency, double size){
		super();
		this.component = component;
		this.potency = potency;
		this.efficiency = efficiency;
		this.size = size;
	}
	
	public double getPotency(){
		return potency;
	}
	
	public double getEfficiency(){
		return efficiency;
	}
	
	public double getSize(){
		return size;
	}
	
	public ComponentBase getEffect(){
		return component;
	}
	
	public void setPotency(double potency){
		this.potency = potency;
	}
	
	public void setEfficiency(double efficiency){
		this.efficiency = efficiency;
	}
	
	public void setSize(double size){
		this.size = size;
	}
	
	public void setEffect(ComponentBase component){
		this.component = component;
	}
	
	@Override
	public boolean isCancelable(){
		return true;
	}
}
