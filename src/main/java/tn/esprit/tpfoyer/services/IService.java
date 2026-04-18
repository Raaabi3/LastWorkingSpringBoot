package tn.esprit.tpfoyer.services;

import java.util.List;

public interface IService <T> {
    public T add(T t);
    public T update(T t);
    public void delete(long idT);
    public T getById(long idT);
    public List<T> getAll();
    public List<T> addAll(List<T> ts);

    // ajouter des interface pour chaque les services
}
