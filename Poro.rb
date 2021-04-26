#!/usr/bin/ruby -w

#This class holds the general information surrounding the record class as well as
#ways for other classes to access information as read only
class Record
  #creates each new record object
  #Params:
  #+countryCode+::the country code of the country
  #+date+::the date in question
  #+cases+::cases on this date
  #+deaths+::deaths on this date
  #+name_fr+::french name of country
  #+name_en+::english name of country
  #
  def initialize(countryCode,date,cases,deaths,name_fr,name_en)
    @@countryCode = countryCode
    @@date = date
    @@cases = cases
    @@deaths = deaths
    @@name_fr = name_fr
    @@name_en = name_en
  end
  #the following methods simply allow access to the class variables of this file.
  def getCountryCode
    return @@countryCode
  end

  def getDate
    return @@date
  end

  def getCases
    return @@cases
  end

  def getDeaths
    return @@deaths
  end

  def getName_fr
    return @@name_fr
  end

  def getName_en
    return @@name_en
  end
end
