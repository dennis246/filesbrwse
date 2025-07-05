package util;

public class Container implements ContainerProps {
    

    Container[] container = new Container[0];

    @Override
    public <E> void add(E[] target, E item) {
        E[] cArray = (E[]) target;
        container = new Container[cArray.length + 1];
        for (int i = 0; i < cArray.length; i++) {
            container[i] = (Container) cArray[i];
        }
        // cArrayInc = cArray;
        container[container.length - 1] = (Container) item;

        //return (E[]) cArrayInc;
    }

    @Override
    public void addAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public Object getFrom(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFrom'");
    }

    @Override
    public void setAt(int index, Object element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAt'");
    }

    @Override
    public void insert(int from, int to) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void remove(int from, int to) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void replace(int from, Object element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

    @Override
    public void replace(Object prevElement, Object newElement) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

   

}
