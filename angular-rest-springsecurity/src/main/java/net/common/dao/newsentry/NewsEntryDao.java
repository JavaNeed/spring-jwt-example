package net.common.dao.newsentry;

import net.common.dao.Dao;
import net.common.entity.NewsEntry;


/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link NewsEntry}s.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public interface NewsEntryDao extends Dao<NewsEntry, Long>
{

}