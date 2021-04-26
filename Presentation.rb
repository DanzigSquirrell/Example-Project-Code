#!/usr/bin/ruby -w
#Handles all user interactions with the program
require './Business.rb'
require './Poro.rb'
require './RecordDisplay.rb'
#this class handles all user interactions and connects with the business class to
#present data to the user
class UserPresentation < RecordDisplay #Implementation of inheritance
  $businessMethods = RecordArray.new
  $businessMethods.generate_array
  #runs the menu of the program including user interface
  #Params:
  #+firstRun+:: A boolean representing whether or not it is the first run of the program
  #             If it is, an extra header is printed
  #
  def menuOptions(firstRun)
      @firstRun = firstRun
      puts "BY DAN SQUIRRELL" #My name to be displayed everywhere always
      if   @firstRun == true
        puts "___________________________________________________"
        puts "\nWelcome to Dan Squirrell's CST8333 Assignment 2!"
      end
      puts "___________________________________________________"
      puts "\nPlease select an option:"
      puts "Option 1: View one or more records"
      puts "Option 2: Create a new record"
      puts "Option 3: Edit an existing record"
      puts "Option 4: Delete an existing record"
      puts "Option 5: Reload records"
      puts "Option 6: Save to CSV"
      puts "Option 7: Exit"
      puts "Option 8: Display graph"
      puts "BY DAN SQUIRRELL"
      puts "___________________________________________________"
      choice = gets.chomp

      case choice #case statement to navigate the user when certain values are entered
        when "1"
          selectRecord
        when "2"
          addRecord
        when "3"
          editRecord
        when "4"
          deleteRecord
        when "5"
          reloadRecords
        when "6"
          writeRecords
        when "7"
          exit  #exit program
        when "8"
          puts "Enter 1 for Cases, 2 for Deaths, or 3 for both"
          @graph_choice = gets.chomp
          $businessMethods.getGraphCount
          displayGraph @graph_choice
          menuOptions false
        else
          menuOptions false #loops through menu until ext
      end
  end
  #this function allows the user to choose between seeing one record, or all records
  #
  def selectRecord
    puts "How many records would you like to display?(Enter 1 for One, or anything else for all)"
    @choice = gets.chomp

    case @choice
    when "1"
      puts "Enter the ID of the record: "
      @id = gets.chomp
      displayRecord "1",@id #prints one record
      menuOptions false
    else
      displayRecord "2",0 #prints all records
      menuOptions false
    end
  end
  #Creates a record to be used to edit or create a new record by the user to be inserted into the Array
  #returns:: the record created
  def createRecord
    puts "Enter the country code: "
    @countryCode = gets.chomp
    puts "Enter the country's englsh name: "
    @name_en = gets.chomp
    puts "Enter the country's french name: "
    @name_fr = gets.chomp
    puts "Enter a date (as yyyy-mm-dd): "
    @date = gets.chomp
    puts "Enter number of cases: "
    @cases = gets.chomp
    puts "Enter number of deaths: "
    @deaths = gets.chomp
    @created_record = Record.new(@countryCode,@name_en,@name_fr,@date,@cases,@deaths)

    return @created_record
  end
  #prompts the user to create a new record, adds it to the array, returns to menu
  def addRecord
    record = createRecord
    $businessMethods.append_to_array record
    menuOptions false
  end
  #prompts user to create a record, finds the record to be updated, updates it, returns to menu
  def editRecord
    puts "Enter the ID of the Record you wish to edit: "
    $businessMethods.editToArray gets.chomp,createRecord
    menuOptions false
  end
  #prompts user to enter an ID of a record to be deleted. Deletes it, returns to menu
  def deleteRecord
    puts "Enter the ID of the Record you wish to delete: "
    @deleteID = gets.chomp
    $businessMethods.deleteFromArray @deleteID
    menuOptions false
  end
  #reloads the array from the CSV and returns to menu
  def reloadRecords
    $businessMethods.reloadArray
    puts "Array Reloaded"
    menuOptions false
  end
  #writes the array to a new CSV file, returns to menu
  def writeRecords
    $businessMethods.writeArray
    puts "Saved to CSV"
    menuOptions false
  end
end
