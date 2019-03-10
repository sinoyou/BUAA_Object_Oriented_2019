package factor;

public class SinFactor extends Factor{
    // protected String base;
    // given power factor's string, generate factor instance.
    public SinFactor(String base) {
        super(base);
    }

    /*@Override
    public boolean equals(Object obj) {
        Boolean flag = classCheck(obj);
        if(flag){
            SinFactor temp = (SinFactor)obj;
            if(!temp.base.equals(this.base)){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public int hashCode() {
        return base.hashCode();
    }*/

}
