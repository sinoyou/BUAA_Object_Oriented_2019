package factor;

public class CosFactor extends Factor{
    protected String base;
    // given power factor's string, generate factor instance.
    public CosFactor(String base) {
        this.base = base;
    }

    @Override
    public boolean equals(Object obj) {
        Boolean flag = classCheck(obj);
        if(flag){
            CosFactor temp = (CosFactor)obj;
            if(!temp.base.equals(this.base)){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public int hashCode() {
        return base.hashCode();
    }

    public String getBase() {
        return base;
    }
}
