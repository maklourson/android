package fr.eni.ecole.demolivedata.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import fr.eni.ecole.demolivedata.bo.Planet;
import fr.eni.ecole.demolivedata.dal.ConnexionDao;
import fr.eni.ecole.demolivedata.dal.PlanetDAO;

/*
Equivalent à un manager
 */
public class PlanetRepository {

    private PlanetDAO dao;
    private LiveData<List<Planet>> planets;

    public PlanetRepository(Application application){
        //Singleton
        dao = ConnexionDao.getPlanetDao(application);
        //Appel unique car observer/LiveData
        planets = dao.getPlanets();
    }

    public LiveData<List<Planet>> findAll(){
        return planets;
    }

    public void insert(Planet planet){
        new insertAsyncTask(dao).execute(planet);
    }

    public void update(Planet planet){
        new updateAsyncTask(dao).execute(planet);
    }

    public void delete(Planet planet){
        new deleteAsyncTask(dao).execute(planet);
    }

    public Planet getPlanetById(int id){
        return dao.getPlanetById(id);
    }

    private static class insertAsyncTask extends AsyncTask<Planet, Void, Void> {

        private PlanetDAO mAsyncTaskDao;

        insertAsyncTask(PlanetDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Planet... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }


    }

    private static class updateAsyncTask extends AsyncTask<Planet, Void, Void> {

        private PlanetDAO mAsyncTaskDao;

        updateAsyncTask(PlanetDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Planet... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Planet, Void, Void> {

        private PlanetDAO mAsyncTaskDao;

        deleteAsyncTask(PlanetDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Planet... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}
