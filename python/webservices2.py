from ctypes import byref, c_int, c_char, c_long, c_short, create_string_buffer
import binascii
import sys
from ctypes import windll
import pypyodbc

from flask import Flask
app = Flask(__name__)


# -----------------------------------------------------------------------------
# Function: dll_version()
# -----------------------------------------------------------------------------
def dll_version():

  #title 
  print ("*** DLL VERSION ***")

  # get handle from DLL
  Handle_HL = windll.LoadLibrary("EpsonFiscalInterface.dll")

  # get dll version
  str_version_max_len = 100
  str_version = create_string_buffer( b'\000' * str_version_max_len )
  int_major = c_int()
  int_minor = c_int()
  error = Handle_HL.ConsultarVersionDll( str_version, str_version_max_len, byref(int_major), byref(int_minor) )
  print ("DllVersion            : ", error)
  #print (error)
  print ("String Dll Version    : ", str_version.value)
  #print (str_version.value)
  print ("Major Dll Version     : ", int_major.value)
  #print (int_major.value)
  print ("Minor Dll Version     : ", int_minor.value)
  #print (int_minor.value)
  
  return  str_version.value
  
 # -----------------------------------------------------------------------------
# Function: print_X_and_Z()
# -----------------------------------------------------------------------------
def print_X_and_Z():

  # get handle from DLL
  Handle_HL = windll.LoadLibrary("EpsonFiscalInterface.dll")

  # connect
  Handle_HL.ConfigurarVelocidad( 9600 )
  Handle_HL.ConfigurarPuerto( "0" )
  error = Handle_HL.Conectar()
  print ("Connect               : ", error)
  
  # print X
  error = Handle_HL.ImprimirCierreX()
  print ("Closure Cashier       : ",error)

  # print Z
  error = Handle_HL.ImprimirCierreZ()
  print ("Closure Day           : ",error)

  # close port
  error = Handle_HL.Desconectar()
  print ("Disconect             : ", error )

def db():

  #http://docs.python.org.ar/tutorial/3/errors.html
  #https://blogs.msdn.microsoft.com/cdndevs/2015/03/11/python-and-data-sql-server-as-a-data-source-for-python-applications/  
	
  print ("Intentando conectar!!!")
  connection = pypyodbc.connect('Driver={SQL Server};'
  'Server=localhost;'
  'Database=VetaroRep;'
  'uid=sa;pwd=cordoba')
  print ("Conectado!!!")  
  cursor = connection.cursor() 
  
  SQLCommandSelectCount = ("SELECT count(*) FROM [dbo].[Cajas]")   
  cursor.execute(SQLCommandSelectCount) 
  results = cursor.fetchone() 
  count = results[0]
  print("Cantidad de cuentas: " + str(count)) 
  
  SQLCommandInsert = ("INSERT INTO [dbo].[Cajas] ([CAJA], [NOMBRE], [CONTROLSTOCK], [DOORNOPERMISO]) VALUES(?, ?, ?, ?)")
  codigoCuenta = count + 10
  print("Nuevo código cuenta: " + str(codigoCuenta)) 
  ValuesInsert = [codigoCuenta, 'Cajon', 1, 10] 
  cursor.execute(SQLCommandInsert, ValuesInsert) 
  connection.commit()
  
  SQLCommandSelect = ("SELECT * FROM [dbo].[Cajas] WHERE [CAJA] = ?") 
  ValuesSelect = [codigoCuenta] 
  cursor.execute(SQLCommandSelect, ValuesSelect) 
  results = cursor.fetchone() 
  print("Datos de la nueva cuenta :: Código: " + str(results[0]) + ", Nombre: " + results[1]) 
  
  
  SQLCommandUpdate = ("UPDATE [dbo].[Cajas] SET [NOMBRE] = ? WHERE [CAJA] = ?")    
  ValuesUpdate = ['Cajon modificado ' + str(codigoCuenta), codigoCuenta] 
  cursor.execute(SQLCommandUpdate, ValuesUpdate) 
  connection.commit()
  
  SQLCommandSelectB = ("SELECT * FROM [dbo].[Cajas]")   
  cursor.execute(SQLCommandSelectB) 
  results = cursor.fetchone() 
  print("Cuentas::")
  while results:
    print("Código: " + str(results[0]) + ", Nombre: " + results[1]) 
    results = cursor.fetchone()
  
  
  connection.close()
   
   

#@app.route("/")
#def hello():    
#	#str_version_r = dll_version()
#	#return str_version_r
#	db()
#
#if __name__ == "__main__":
#    app.run()

db()