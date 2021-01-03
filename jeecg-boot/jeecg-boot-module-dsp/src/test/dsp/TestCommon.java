package dsp;

import io.swagger.models.auth.In;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCommon {

    @Test
    public void tree() {
        List<A> list = new ArrayList<>();
        list.add(new A(310, 31));
        list.add(new A(200, 20));
        list.add(new A(100, 10));
        list.add(new A(31, 3));
        list.add(new A(30, 3));
        list.add(new A(20, 2));
        list.add(new A(11, 1));
        list.add(new A(10, 1));
        list.add(new A(3, 0));
        list.add(new A(2, 0));
        list.add(new A(1, 0));
        Map<Integer, A> map = getTree(list);
        System.out.println("");
    }

    private Map<Integer, A> getTree(List<A> list) {
        Map<Integer, A> map = new HashMap<>();
        for (A item : list) {
            map.put(item.getId(), item);
        }
        for (A item : list) {
            if (map.containsKey(item.getPid())) {
                map.get(item.getPid()).getChildren().add(item);
                map.get(item.getPid()).setLeaf(false);
            }
        }
        for (A item : list) {
            if (item.getPid() != 0) {
                map.remove(item.getId());
            }
        }
        return map;
    }


    @Data
    class A {
        private int id;
        private int pid;
        private List<A> children;
        private boolean isLeaf;

        public A(int id, int pid) {
            this.id = id;
            this.pid = pid;
            this.children = new ArrayList<>();
            this.isLeaf = true;
        }
    }


}
