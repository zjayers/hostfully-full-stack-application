export const createBlockOrBooking = async (endpoint: string, data: { startDate: string, endDate: string }) => {
  const response = await fetch(endpoint, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message);
  }

  return response.json();
};

export const updateBlockOrBooking = async (endpoint: string, id: string, data: { startDate: string, endDate: string }) => {
  const response = await fetch(`${endpoint}/${id}`, {
    method: 'PUT', // or PATCH, depending on your API
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message);
  }

  return response.json();
};

export const deleteBlockOrBooking = async (endpoint: string, id: string) => {
  const response = await fetch(`${endpoint}/${id}`, {
    method: 'DELETE',
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message);
  }

  return response.json();
};
