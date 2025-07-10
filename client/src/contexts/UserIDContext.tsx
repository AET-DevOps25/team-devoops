import React, { createContext, useContext, useEffect, useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import { useUserService } from '../services/userService';

const UserIDContext = createContext<string | null>(null);

export const useUserID = () => useContext(UserIDContext);

export const UserIDProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { user } = useAuth0();
  const { getCurrentUser } = useUserService();
  const [userID, setUserID] = useState<string | null>(null);

  useEffect(() => {
    let isMounted = true;
    const fetchUserID = async () => {
      if (!user?.sub) return;
      try {
        const res = await getCurrentUser(user.sub);
        if (isMounted) setUserID(res.userID);
      } catch {
        if (isMounted) setUserID(null);
      }
    };
    fetchUserID();
    return () => {
      isMounted = false;
    };
  }, [user?.sub]);

  return <UserIDContext.Provider value={userID}>{children}</UserIDContext.Provider>;
};
