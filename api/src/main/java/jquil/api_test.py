import json
import requests
import sys
import struct
from six import integer_types
import numpy as np

url = "https://api.rigetti.com/qvm"



api_key = "nmRPAVunQl19TtQz9eMd11iiIsArtUDTaEnsSV6u"
user_id = "4c2166b5-a99d-433c-a289-afcfcd1f1bda"

headers = {'Accept': 'application/octet-stream',
           'X-User-Id' : user_id,
           "X-Api-Key": api_key}


program = "X 0\n"

payload = {
    "type": "wavefunction",
    "addresses": [0],
    "trials": 1,
    "compiled-quil": program

    }


response = requests.post(url, data=json.dumps(payload), headers=headers)

num_octets = len(response.content)

#65
#print(num_octets)

#print (sys.getsizeof(response.text))


def _round_to_next_multiple(n, m):
    """
    Round up the the next multiple.
    :param n: The number to round up.
    :param m: The multiple.
    :return: The rounded number
    """
    return n if n % m == 0 else n + m - n % m


def _octet_bits(o):
    """
    Get the bits of an octet.
    :param o: The octets.
    :return: The bits as a list in LSB-to-MSB order.
    :rtype: list
    """
    if not isinstance(o, integer_types):
        raise TypeError("o should be an int")
    if not (0 <= o <= 255):
        raise ValueError("o should be between 0 and 255 inclusive")
    bits = [0] * 8
    for i in range(8):
        if 1 == o & 1:
            bits[i] = 1
            o = o >> 1
    return bits

OCTETS_PER_DOUBLE_FLOAT = 8
OCTETS_PER_COMPLEX_DOUBLE = 2 * OCTETS_PER_DOUBLE_FLOAT

def from_bit_packed_string(coef_string, classical_addresses):
    """
        From a bit packed string, unpacks to get the wavefunction and classical measurement results
        :param bytes coef_string:
        :param list classical_addresses:
        :return:
        """
    coef_string =  coef_string[1:]
    print(list(coef_string))
    num_octets = len(coef_string)
    print("Num octets " , num_octets)
    num_addresses = len(classical_addresses)
    
    print("Num addresses " , num_addresses)
    num_memory_octets = _round_to_next_multiple(num_addresses, 8) // 8
    print("Round to multiple " ,  _round_to_next_multiple(num_addresses, 8))
    print("Num memory " ,num_memory_octets)
    num_wavefunction_octets = num_octets - num_memory_octets
    print("Num wavefunction " , num_wavefunction_octets)
    
    # Parse the classical memory
    mem = []
    for i in range(num_memory_octets):
        octet = struct.unpack('B', coef_string[i:i + 1])[0]
        mem.extend(_octet_bits(octet))
        
    mem = mem[0:num_addresses]
    
    # Parse the wavefunction
    wf = np.zeros(num_wavefunction_octets // OCTETS_PER_COMPLEX_DOUBLE, dtype=np.cfloat)
    print("wf " , wf)


    print("range " , range(num_memory_octets, num_octets, OCTETS_PER_COMPLEX_DOUBLE))
    for i, p in enumerate(range(num_memory_octets, num_octets, OCTETS_PER_COMPLEX_DOUBLE)):
        print("I ", p)
        #print("P ",  p + OCTETS_PER_DOUBLE_FLOAT)

        
        re_be = coef_string[p: p + OCTETS_PER_DOUBLE_FLOAT]
        print("re_be ", len(re_be))
        im_be = coef_string[p + OCTETS_PER_DOUBLE_FLOAT: p + OCTETS_PER_COMPLEX_DOUBLE]
        #print("im_be ", im_be)
        re = struct.unpack('>d', re_be)[0]
        #print("re ", re)
        im = struct.unpack('>d', im_be)[0]
        wf[i] = complex(re, im)

        
    return wf



print(from_bit_packed_string(response.content, []))
